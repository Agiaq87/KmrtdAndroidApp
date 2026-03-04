package it.giaquinto.kmrtd.kmrtdexampleapp.framework

import android.graphics.BitmapFactory
import android.nfc.tech.IsoDep
import com.gemalto.jp2.JP2Decoder
import it.giaquinto.kmrtd.kmrtdexampleapp.model.MRZInput
import kmrtd.BACKey
import kmrtd.PACEKeySpec
import kmrtd.PassportService
import kmrtd.lds.CardAccessFile
import kmrtd.lds.DataGroup
import kmrtd.lds.PACEInfo
import kmrtd.lds.SODFile
import kmrtd.lds.SecurityInfo
import kmrtd.lds.icao.COMFile
import kmrtd.lds.icao.DG11File
import kmrtd.lds.icao.DG12File
import kmrtd.lds.icao.DG14File
import kmrtd.lds.icao.DG15File
import kmrtd.lds.icao.DG1File
import kmrtd.lds.icao.DG2File
import kmrtd.lds.icao.DG3File
import kmrtd.lds.icao.DG4File
import kmrtd.lds.icao.DG5File
import kmrtd.lds.icao.DG6File
import kmrtd.lds.icao.DG7File
import kmrtd.lds.icao.MRZInfo
import kmrtd.lds.iso19794.FaceInfo
import kmrtd.protocol.BACResult
import kmrtd.protocol.PACEResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import net.sf.scuba.smartcards.CardService
import java.io.ByteArrayInputStream
import java.security.MessageDigest
import java.security.SecureRandom
import java.security.Signature
import java.security.interfaces.ECPublicKey
import java.security.interfaces.RSAPublicKey
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date
import java.util.Locale

class KmrtdManager {
    // Update UI
    private val _state: MutableStateFlow<KmrtdState> = MutableStateFlow(KmrtdState.Idle)
    val state: StateFlow<KmrtdState> = _state.asStateFlow()

    suspend fun start(isoDep: IsoDep, mrzInput: MRZInput, isCie: Boolean) =
        withContext(Dispatchers.IO) {
            isoDep.timeout = 10000 // 10 seconds
            val kmrtdResultBuilder = KmrtdResultBuilder()
            kmrtdResultBuilder.initialTimeStamp = System.currentTimeMillis()

            val cardService: CardService = initCardService(isoDep).getOrElse {
                _state.value = KmrtdState.Error(
                    KmrtdError.CARD_SERVICE_CONNECTION_FAILED,
                    it
                )
                return@withContext
            }

            val passportService: PassportService =
                initPassportService(cardService, isCie).getOrElse {
                    _state.value = KmrtdState.Error(
                        KmrtdError.PASSPORT_SERVICE_CONNECTION_FAILED,
                        it
                    )
                    return@withContext
                }

            val securityInfos: List<SecurityInfo> = readSecurityInfos(passportService).getOrElse {
                _state.value = KmrtdState.Error(
                    KmrtdError.SECURITY_INFOS_READING_ERROR,
                    it
                )
                return@withContext
            }

            val paceInfos: List<PACEInfo> = searchPaceInfos(securityInfos)

            val bacKey: BACKey = makeBACKey(mrzInput).getOrElse {
                _state.value = KmrtdState.Error(
                    KmrtdError.BAC_KEY_GENERATION_ERROR,
                    it
                )
                return@withContext
            }

            val paceKey: PACEKeySpec = makePACEKey(bacKey).getOrElse {
                _state.value = KmrtdState.Error(
                    KmrtdError.PACE_KEY_GENERATION_ERROR,
                    it
                )
                return@withContext
            }

            val paceResult: PACEResult? = startPACE(passportService, paceInfos, paceKey).getOrNull()
            var bacResult: BACResult? = null

            if (paceResult != null) {
                _state.value = KmrtdState.PACESuccess

                sendSelectedApplet(passportService).getOrElse {
                    _state.value = KmrtdState.Error(
                        KmrtdError.SEND_SELECTED_APPLET_ERROR,
                        it
                    )
                    return@withContext
                }
            } else {
                _state.value = KmrtdState.FallbackToBAC

                bacResult = readWithBAC(passportService, bacKey).fold(
                    onSuccess = {
                        _state.value = KmrtdState.BACSuccess
                        it
                    },
                    onFailure = {
                        _state.value = KmrtdState.Error(
                            KmrtdError.BAC_ERROR,
                            it
                        )
                        null
                    }
                )
            }

            if (
                paceResult == null &&
                bacResult == null
            ) {
                _state.value = KmrtdState.Error(
                    KmrtdError.CONNECTION_FAILED
                )
                return@withContext
            }

            val comFile: COMFile? = readOnlyFile {
                COMFile(passportService.getInputStream(PassportService.EF_COM))
            }
            if (comFile == null) {
                _state.value = KmrtdState.Error(
                    KmrtdError.COM_FILE_ERROR
                )
                return@withContext
            }

            val sodFile: SODFile? = readOnlyFile {
                SODFile(passportService.getInputStream(PassportService.EF_SOD))
            }
            if (sodFile == null) {
                _state.value = KmrtdState.Error(
                    KmrtdError.SOD_FILE_ERROR
                )
                return@withContext
            }

            val dataGroups: MutableMap<Int, DataGroup> = mutableMapOf()
            val dataGroupsEncodedByteArray: MutableMap<Int, ByteArray> = mutableMapOf()

            val dg1File = readFileAndStream(passportService, PassportService.EF_DG1) {
                DG1File(it)
            }
            if (dg1File == null) {
                _state.value = KmrtdState.Error(
                    KmrtdError.DG1_FILE_ERROR
                )
                return@withContext
            } else {
                dataGroups[1] = dg1File.first
                dataGroupsEncodedByteArray[1] = dg1File.second
            }

            val dg2File = readFileAndStream(passportService, PassportService.EF_DG2) {
                DG2File(it)
            }
            if (dg2File == null) {
                _state.value = KmrtdState.Error(
                    KmrtdError.DG2_FILE_ERROR
                )
                return@withContext
            } else {
                dataGroups[2] = dg2File.first
                dataGroupsEncodedByteArray[2] = dg2File.second
            }

            val dg3File = readFileAndStream(passportService, PassportService.EF_DG3) {
                DG3File(it)
            }
            dg3File?.let {
                dataGroups[3] = it.first
                dataGroupsEncodedByteArray[3] = it.second
            }

            val dg4File = readFileAndStream(passportService, PassportService.EF_DG4) {
                DG4File(it)
            }
            dg4File?.let {
                dataGroups[4] = it.first
                dataGroupsEncodedByteArray[4] = it.second
            }

            val dg5File = readFileAndStream(passportService, PassportService.EF_DG5) {
                DG5File(it)
            }
            dg5File?.let {
                dataGroups[5] = it.first
                dataGroupsEncodedByteArray[5] = it.second
            }

            val dg6File = readFileAndStream(passportService, PassportService.EF_DG6) {
                DG6File(it)
            }
            dg6File?.let {
                dataGroups[6] = it.first
                dataGroupsEncodedByteArray[6] = it.second
            }

            val dg7File = readFileAndStream(passportService, PassportService.EF_DG7) {
                DG7File(it)
            }
            dg7File?.let {
                dataGroups[7] = it.first
                dataGroupsEncodedByteArray[7] = it.second
            }

            val dg11File = readFileAndStream(passportService, PassportService.EF_DG11) {
                DG11File(it)
            }
            dg11File?.let {
                dataGroups[11] = it.first
                dataGroupsEncodedByteArray[11] = it.second
            }

            val dg12File = readFileAndStream(passportService, PassportService.EF_DG12) {
                DG12File(it)
            }
            dg12File?.let {
                dataGroups[12] = it.first
                dataGroupsEncodedByteArray[12] = it.second
            }

            val dg14File = readFileAndStream(passportService, PassportService.EF_DG14) {
                DG14File(it)
            }
            dg14File?.let {
                dataGroups[14] = it.first
                dataGroupsEncodedByteArray[14] = it.second
            }

            val dg15File = readFileAndStream(passportService, PassportService.EF_DG15) {
                DG15File(it)
            }
            dg15File?.let {
                dataGroups[15] = it.first
                dataGroupsEncodedByteArray[15] = it.second
            }


            extractFromSOD(sodFile, kmrtdResultBuilder)
            extractFromDG1(dg1File.first, kmrtdResultBuilder)
            extractFromDG2(dg2File.first, kmrtdResultBuilder)
            dg7File?.let {
                extractFromDG7(dg7File.first, kmrtdResultBuilder)
            }
            dg11File?.let {
                extractFromDG11(dg11File.first, kmrtdResultBuilder)
            }
            dg12File?.let {
                extractFromDG12(dg12File.first, kmrtdResultBuilder)
            }


            kmrtdResultBuilder.passiveAuthentication = verifyHashForPassiveAuthentication(
                sodFile,
                dataGroupsEncodedByteArray
            )

            dg15File?.let {
                kmrtdResultBuilder.activeAuthentication = activeAuthentication(
                    passportService,
                    it.first
                )
            }
            dg14File?.let {
                kmrtdResultBuilder.chipAuthentication = chipAuthentication(
                    passportService,
                    it.first
                )
            }

            kmrtdResultBuilder.isSuccess = true
            _state.value = KmrtdState.Success(
                paceResult != null,
                kmrtdResultBuilder,
                mrzInput
            )

            kmrtdResultBuilder.initialTimeStamp?.let {
                kmrtdResultBuilder.elapsedTimeStamp = elapsed(it)
            }
        }

    // 1)
    private fun initCardService(isoDep: IsoDep): Result<CardService> = runCatching {
        _state.value = KmrtdState.CardService

        CardService.getInstance(isoDep).also {
            it.open()
        }
    }

    // 2)
    private fun initPassportService(
        cardService: CardService,
        isCie: Boolean
    ): Result<PassportService> = runCatching {
        _state.value = KmrtdState.PassportService

        PassportService(
            cardService,
            PassportService.NORMAL_MAX_TRANCEIVE_LENGTH,
            PassportService.DEFAULT_MAX_BLOCKSIZE,
            !isCie, // Compatible with Italian CIE
            true // whether the secure messaging channels, resulting from BAC, PACE, EAC-CA, should check MACs on response APDUs
        ).also {
            it.open()
        }

    }

    // 3)
    private fun readSecurityInfos(passportService: PassportService): Result<List<SecurityInfo>> =
        runCatching {
            _state.value = KmrtdState.SecurityInfos

            CardAccessFile(
                passportService.getInputStream(
                    PassportService.EF_CARD_ACCESS,
                    PassportService.DEFAULT_MAX_BLOCKSIZE
                )
            ).securityInfos.toList()
        }

    // 4)
    private fun searchPaceInfos(securityInfos: List<SecurityInfo>): List<PACEInfo> =
        securityInfos.filterIsInstance<PACEInfo>().also {
            _state.value = KmrtdState.PaceInfos
        }

    // 5)
    private fun makeBACKey(mrzInput: MRZInput): Result<BACKey> = runCatching {
        _state.value = KmrtdState.BACKey

        BACKey(
            mrzInput.documentNumber,
            mrzInput.birthDate,
            mrzInput.expireDate
        )
    }

    // 6)
    private fun makePACEKey(bacKey: BACKey): Result<PACEKeySpec> = runCatching {
        _state.value = KmrtdState.PACEKey

        PACEKeySpec.createMRZKey(
            bacKey
        )
    }

    // 7)
    private fun startPACE(
        passportService: PassportService,
        paceInfos: List<PACEInfo>,
        paceKey: PACEKeySpec
    ): Result<PACEResult> = runCatching {
        _state.value = KmrtdState.PACEConnection

        paceInfos.firstNotNullOfOrNull { paceInfo ->
            readWithPACE(passportService, paceKey, paceInfo)
        } ?: throw Exception("No PACE connection")
    }

    // 7.1)
    private fun readWithPACE(
        passportService: PassportService,
        paceKey: PACEKeySpec,
        paceInfo: PACEInfo
    ): PACEResult? = runCatching {
        passportService.doPACE(
            paceKey,
            paceInfo.objectIdentifier,
            PACEInfo.toParameterSpec(paceInfo.parameterId),
            paceInfo.parameterId
        )
    }.getOrNull()

    // 7.2)
    private fun readWithBAC(
        passportService: PassportService,
        bacKey: BACKey
    ): Result<BACResult> = runCatching {
        passportService.doBAC(bacKey)
    }

    // 8)
    fun sendSelectedApplet(passportService: PassportService) = runCatching {
        passportService.sendSelectApplet(true)
    }

    // 9)
    private fun <FILE> readOnlyFile(block: () -> FILE): FILE? = runCatching {
        block()
    }.getOrNull()

    // 9.1)
    private fun <FILE> readFileAndStream(
        passportService: PassportService,
        file: Short,
        block: (ByteArrayInputStream) -> FILE
    ): Pair<FILE, ByteArray>? = runCatching {
        val stream = passportService.getInputStream(file)
        val rawBytes: ByteArray = stream.readBytes()
        Pair(block(ByteArrayInputStream(rawBytes)), rawBytes)
    }.getOrNull()

    // 10)
    private fun extractFromSOD(sod: SODFile, builder: KmrtdResultBuilder) = with(builder) {
        sodBase64 = Base64.getEncoder().encodeToString(sod.encoded)
        dataGroupHashes = mutableMapOf()
        if (sod.dataGroupHashes.isNotEmpty()) {
            for (dg in sod.dataGroupHashes.keys) {
                dataGroupHashes[dg.toString()] = sod.dataGroupHashes[dg].toString() ?: ""
            }
        }
        val cert = sod.docSigningCertificate
        emitter = cert.issuerX500Principal.name
        holder = cert.subjectX500Principal.name
        validFrom = cert.notBefore
        validFromString = cert.notBefore.toString()
        validTo = cert.notAfter
        validToString = cert.notAfter.toString()
        serialNumber = cert.serialNumber.toString(16).uppercase()
        publicKeyAlgorithm = cert.publicKey.algorithm
        signatureAlgorithm = cert.sigAlgName

        val md = MessageDigest.getInstance("SHA-1")
        val digest = md.digest(cert.encoded)
        certificateFingerprint = digest.joinToString(":") { "%02X".format(it) }

        _state.value = KmrtdState.SODFile
    }

    // 11)
    private fun extractFromDG1(dg1: DG1File, builder: KmrtdResultBuilder) = with(builder) {
        dg1.mrzInfo?.let { mrzInfo ->
            gender = mrzInfo.genderCode
            documentNumber = mrzInfo.documentNumber
            expirationDateString = mrzInfo.dateOfExpiry.toString()
            nationality = mrzInfo.nationality
            releasingNationCode = mrzInfo.issuingState
            releasingNation = Locale("ENG", mrzInfo.issuingState).displayCountry
            val names = mrzInfo.primaryIdentifier.split("<<")
            lastName = names.getOrNull(0)
            firstName = mrzInfo.secondaryIdentifier
            personName = "$lastName $firstName"
            documentType = when (mrzInfo.documentType) {
                MRZInfo.DOC_TYPE_ID1 -> "TD1"
                MRZInfo.DOC_TYPE_ID2 -> "TD2"
                MRZInfo.DOC_TYPE_ID3 -> "TD3"
                else -> "NA"
            }
            issuingState = mrzInfo.issuingState
            gender = mrzInfo.genderCode

            _state.value = KmrtdState.DG1File
        }
    }

    // 12)
    private fun extractFromDG2(dg2: DG2File, builder: KmrtdResultBuilder) = with(builder) {
        dg2.subRecords?.filterIsInstance<FaceInfo>()?.firstOrNull()?.faceImageInfos?.firstOrNull()
            ?.let {
                it.imageInputStream?.readBytes()?.let { byteArray ->
                    personImage = when (it.mimeType) {
                        "image/jp2", "image/jpeg2000" -> JP2Decoder(byteArray).decode()
                        else -> BitmapFactory.decodeByteArray(
                            byteArray,
                            0,
                            byteArray.size
                        )
                    }
                }

                eyeColor = it.eyeColor
                // Added from KMRTD
                /*imageColorSpace = it.colorSpace
                imageDataType = it.imageDataType
                faceImageDataType = it.faceImageType
                hairColor = it.hairColor
                //feature = it.featureMask
                sourceType = it.sourceType
                expression = it.expression*/
                // Added from KMRTD
                gender = it.gender

                _state.value = KmrtdState.DG2File
            }
    }

    // 13)
    private fun extractFromDG7(dg7: DG7File, builder: KmrtdResultBuilder) = with(builder) {
        dg7.images.firstOrNull()?.let {
            signatureImage = BitmapFactory.decodeByteArray(
                it.encoded,
                0,
                it.encoded.size
            )
            signatureImageBase64 = Base64.getEncoder().encodeToString(
                it.encoded
            )
            _state.value = KmrtdState.DG7File
        }
    }

    // 14)
    /**
     * File structure for the EF_DG11 file. Datagroup 11 contains additional personal detail(s). All fields are optional. See Section 16 of LDS-TR.
     * Name of Holder (Primary and Secondary Identifiers, in full)
     * Other Name(s)
     * Personal Number
     * Place of Birth
     * Date of Birth (in full)
     * Address
     * Telephone Number(s)
     * Profession
     * Title
     * Personal Summary
     * Proof of Citizenship [see 14.5.1]
     * Number of Other Valid Travel Documents
     * Other Travel Document Numbers
     * Custody Information
     * @see https://javadoc.io/static/org.jmrtd/jmrtd/0.8.3/org/jmrtd/lds/icao/DG11File.html
     */
    private fun extractFromDG11(dg11: DG11File, builder: KmrtdResultBuilder) = with(builder) {
        val birth = dg11.fullDateOfBirth.toDateOrNull("yyyyMMdd")
        dateOfBirth = birth
        dateOfBirthString = birth.toString()
        address = dg11.permanentAddress?.get(0)
        city = dg11.permanentAddress?.get(1)
        state = dg11.permanentAddress?.get(2)
        otherNames = dg11.otherNames?.joinToString(", ")
        stateOfBirth = dg11.placeOfBirth?.joinToString(", ")
        cityOfBirth = dg11.placeOfBirth?.firstOrNull()
        telephone = dg11.telephone
        profession = dg11.profession
        title = dg11.title
        custody = dg11.custodyInformation
        personalNotes = dg11.personalSummary
        taxId = dg11.personalNumber
    }

    // 15)
    /**
     * File structure for the EF_DG12 file. Datagroup 12 contains additional document detail(s).
     * @see https://javadoc.io/static/org.jmrtd/jmrtd/0.8.3/org/jmrtd/lds/icao/DG12File.html
     */
    private fun extractFromDG12(dg12: DG12File, builder: KmrtdResultBuilder) = with(builder) {
        val pers = dg12.dateAndTimeOfPersonalization?.toDateOrNull("yyyyMMdd")
        val issue = dg12.dateOfIssue.toDateOrNull("yyyyMMdd")
        dateOfIssue = issue
        dateOfIssueString = issue.toString()
        issuingAuthority = dg12.issuingAuthority
        taxOrExitRequirements = dg12.taxOrExitRequirements
        personalizationDate = pers
        personalizationDateString = pers.toString()
        systemSerialNumber = dg12.personalizationSystemSerialNumber
        observations = dg12.endorsementsAndObservations
    }

    // 16)
    /**
     * Passive Authentication
     * Passive Authentication (PA) proves that the contents of the SOD and the LDS are authentic and not changed.
     *
     * Passive Authentication uses the mechanism of digital signature to confirm the authenticity of data that are stored in RFID-chip memory. It allows detecting the presence of any changes in signed data read from the RFID-chip memory but does not protect against their full copying (cloning of RFID-chip).
     *
     * To use the digital signature mechanism requires a pair of cryptographic keys. The private key is used to compute the digital signature and is available only for the signer; the public key – to verify the signature value and is distributed as a certificate (a special data object, which is protected by the digital signature mechanism as well).
     *
     * Thus, the procedure of passive authentication consists of two basic stages to control:
     *
     * the authenticity of document security object;
     * integrity of document data informational groups.
     * To verify the authenticity of electronic documents with the help of the PA it is required to:
     *
     * read SOD data from the memory of RFID-chip;
     * receive DS-certificate with a public key to verify a digital signature of SOD;
     * receive CSCA-certificate (Country Signing Certificate Authority) with a public key to verify a digital signature of DS-certificate;
     * verify the authenticity of the CSCA-certificate by verification of its digital signature (since it is self-signed, the signature verification may be performed using the public key contained in the certificate itself);
     * verify the authenticity of the DS-certificate by verification of its digital signature;
     * verify the authenticity of the SOD by verification of its digital signature;
     * verify the authenticity of the read informational data groups by comparing the computed hash values and the corresponding values contained in the SOD.
     * Since Master Lists (ML) can be used as a storage for CSCA-certificates for SOD verification, a validation of the digital signature of the master list's security object (SOML) is a part of passive authentication. This digital signature is generated at the stage of master list issuance on the basis of its contents by the issuer (so called "Master List Signer", MLS).
     *
     * To verify the authenticity of the master list it is required to:
     *
     * receive MLS-certificate with a public key to verify a digital signature of SOML;
     * receive CSCA-certificate with a public key to verify a digital signature of MLS-certificate;
     * verify the authenticity of the CSCA-certificate by verification of its digital signature (since it is self-signed, the signature verification may be performed using the public key contained in the certificate itself);
     * verify the authenticity of the MLS-certificate by verification of its digital signature;
     * verify the authenticity of the SOML by verification of its digital signature.
     * Search for a public key to verify a digital signature can be performed by one of two available criteria:
     *
     * a combination of the identifier of the source (organization), which has issued the respective certificate (Issuer), and the certificate serial number (serialNumber);
     * identifier of the signature subject (the organization that performed document personalization) (subjectKeyIdentifier).
     * Access to the CSCA-, DS- and MLS- certificates must be provided within the context of the policy of providing the terminal functioning. As a rule, local or centralized certificate storage – Public Key Directory (PKD) – is used for these purposes. In most cases the DS-certificate is included directly in the data structure of SOD; MLS- and corresponding CSCA-certificate can be present in SOML data structure.
     * see https://www2023.icao.int/publications/documents/9303_p11_cons_en.pdf or https://docs.regulaforensics.com/develop/doc-reader-sdk/overview/security-mechanisms-for-electronic-documents/
     * https://docs.regulaforensics.com/develop/doc-reader-sdk/overview/security-mechanisms-for-electronic-documents/#passive-authentication
     */
    private fun verifyHashForPassiveAuthentication(
        sodFile: SODFile,
        dataGroupsEncodedByteArray: Map<Int, ByteArray>,
    ): Boolean = runCatching {
        val digestAlgorithm = sodFile.digestAlgorithm ?: return false
        val dataGroupHashes = sodFile.dataGroupHashes ?: return false
        val messageDigest = MessageDigest.getInstance(digestAlgorithm)
        var passiveAuthenticationPassed = true

        dataGroupsEncodedByteArray.forEach { (key, value) ->
            passiveAuthenticationPassed =
                passiveAuthenticationPassed && messageDigest.digest(
                    value
                ).contentEquals(dataGroupHashes[key])
        }

        return passiveAuthenticationPassed
    }.getOrElse { false }

    // 17)
    /**
     * Active Authentication
     * Active Authentication (AA) uses a mechanism of "challenge – response" to determine the authenticity of RFID-chip.
     *
     * A pair of cryptographic keys is required for its operation:
     *
     * the private key – is stored in protected memory of the RFID-chip and is inaccessible for reading;
     * the public key – is stored in a special informational data group DG15 of ePassport application (for other applications AA is not provided).
     * In a process of active authentication, the terminal sends a randomly selected data fragment ("challenge") to the RFID-chip. The chip generates a digital signature of the data using the private key and returns its value ("response") to the terminal. The terminal verifies the validity of the digital signature using the public key, determining thereby the authenticity of the private key used by the chip, and hence the one of the chip itself.
     *
     * Active Authentication prevents copying the SOD and proves that it has been read from the authentic contactless integrated circuit (IC) and proves that the contactless IC has not been substituted. Support for Active Authentication is indicated by the presence of DG15.
     */
    private fun activeAuthentication(
        passportService: PassportService,
        dg15File: DG15File
    ): Boolean = runCatching {
        var keySize = 0
        val publicKey = dg15File.publicKey
        val challenge = ByteArray(8).apply {
            SecureRandom().nextBytes(this)
        }

        val (digestAlgorithm, signatureAlgorithm) = when (publicKey.algorithm) {
            "RSA" -> {
                keySize = (publicKey as RSAPublicKey).modulus.bitLength()
                if (keySize < 2048) {
                    "SHA-1" to "SHA1withRSA"  // Old chip
                } else {
                    "SHA-256" to "SHA256withRSA"  // New chip
                }
            }

            "EC", "ECDSA" -> {
                val keySize = (publicKey as ECPublicKey).params.order.bitLength()
                if (keySize < 256) {
                    "SHA-1" to "SHA1withECDSA"  // Old chip
                } else {
                    "SHA-256" to "SHA256withECDSA"  // New chip
                }
            }

            else -> {
                return false
            }
        }

        val response = passportService.doAA(
            dg15File.publicKey,
            digestAlgorithm,
            signatureAlgorithm,
            challenge
        )

        val signature = Signature.getInstance(signatureAlgorithm)
        signature.initVerify(dg15File.publicKey)
        signature.update(challenge)
        return signature.verify(response.response)
    }.getOrElse { false }

    // 18)
    /**
     * Chip Authentication
     * Chip Authentication procedure is one of the components of EAC/EAP. Like BAC/BAP and PACE, it serves to organize a secure communication channel, which is more reliable compared to the basic procedures. In addition, CA is an alternative to AA, as it confirms the chip authenticity as well.
     *
     * CA is based on the use of a static pair of cryptographic keys, which are stored in chip memory.
     *
     * Support of Chip Authentication is indicated by the presence of corresponding SecurityInfos in DG14.
     *
     * Successful CA procedure ensures that the public key and the private key stored in the protected chip memory comply with each other. And this in turn confirms that the chip has not been cloned.
     */
    private fun chipAuthentication(
        passportService: PassportService,
        dG14File: DG14File
    ): Boolean = runCatching {
        val caInfos = dG14File.chipAuthenticationInfos
        val caPublicKeyInfos =
            dG14File.chipAuthenticationPublicKeyInfos

        if (caInfos.isEmpty() || caPublicKeyInfos.isEmpty()) {
            return false
        }

        val caInfo = caInfos.first()
        val caPublicKeyInfo = caPublicKeyInfos.first()
        val caPublicKey = caPublicKeyInfo.subjectPublicKey

        val caResult = passportService.doEACCA(
            caPublicKeyInfo.keyId,              // Key identifier
            caInfo.objectIdentifier,             // Protocol OID (es. id-CA-DH-AES-CBC-CMAC-128)
            caPublicKeyInfo.objectIdentifier,    // Public key OID
            caPublicKey                          // Public key da DG14
        )

        return !(caResult == null || caResult.wrapper == null || caResult.publicKey == null)
    }.getOrElse { false }

    private fun elapsed(fromTimestamp: Long): Long =
        (System.currentTimeMillis() - fromTimestamp)

    fun String.toDateOrNull(pattern: String = "dd-MM-yyyy"): Date? = runCatching {
        SimpleDateFormat(pattern, Locale.getDefault()).parse(this)
    }.getOrNull()
}