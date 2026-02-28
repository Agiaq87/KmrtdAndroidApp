package it.giaquinto.kmrtd.kmrtdexampleapp.framework

import android.graphics.Bitmap
import net.sf.scuba.data.Gender
import org.jmrtd.lds.DataGroup
import org.jmrtd.lds.iso19794.FaceImageInfo
import java.util.Date

class KmrtdResultBuilder {
    var issuingAuthority: String? = null
    var issuingState: String? = null
    var certificateFingerprint: String? = null
    var city: String? = null
    var cityOfBirth: String? = null
    var custody: String? = null
    var dateOfBirth: Date? = null
    var dateOfBirthString: String? = null
    var dateOfIssue: Date? = null
    var dateOfIssueString: String? = null
    var documentNumber: String? = null
    var documentType: String = "TD1"
    var emitter: String? = null
    var expirationDate: Date? = null
    var expirationDateString: String? = null
    var eyeColor: FaceImageInfo.EyeColor? = null
    var firstName: String? = null
    var gender: Gender? = null
    var holder: String? = null
    var address: String? = null
    var lastName: String? = null
    var nationality: String? = null
    var observations: String? = null
    var otherNames: String? = null
    var personBirthdayString: String? = null
    var personalizationDate: Date? = null
    var personalizationDateString: String? = null
    var personImage: Bitmap? = null
    var personName: String? = null
    var personalNotes: String? = null
    var stateOfBirth: String? = null
    var profession: String? = null
    var publicKeyAlgorithm: String? = null
    var releasingNation: String? = null
    var releasingNationCode: String? = null
    var passiveAuthentication: Boolean = false
    var activeAuthentication: Boolean = false
    var chipAuthentication: Boolean = false
    var serialNumber: String? = null
    var signatureAlgorithm: String? = null
    var signatureImage: Bitmap? = null
    var signatureImageBase64: String? = null
    var state: String? = null
    var systemSerialNumber: String? = null
    var taxId: String? = null
    var taxOrExitRequirements: String? = null
    var telephone: String? = null
    var title: String? = null
    var validFrom: Date? = null
    var validFromString: String? = null
    var validTo: Date? = null
    var validToString: String? = null
    var sodBase64: String? = null
    var dataGroups: MutableMap<Int, DataGroup>? = null
    var dataGroupHashes: MutableMap<String, String> = mutableMapOf()
    var initialTimeStamp: Long? = null
    var elapsedTimeStamp: Long? = null
    var isSuccess: Boolean = false

    override fun toString(): String =
        """
        KmrtdResultBuilder(
            issuingAuthority=$issuingAuthority, 
            issuingState=$issuingState, 
            certificateFingerprint=$certificateFingerprint, 
            city=$city, 
            cityOfBirth=$cityOfBirth, 
            custody=$custody, 
            dateOfBirth=$dateOfBirth, 
            dateOfBirthString=$dateOfBirthString, 
            dateOfIssue=$dateOfIssue, 
            dateOfIssueString=$dateOfIssueString, 
            documentNumber=$documentNumber, 
            documentType='$documentType', 
            emitter=$emitter, 
            expirationDate=$expirationDate, 
            expirationDateString=$expirationDateString, 
            eyeColor=$eyeColor, 
            firstName=$firstName, 
            gender=$gender, 
            holder=$holder, 
            address=$address, 
            lastName=$lastName, 
            nationality=$nationality, 
            observations=$observations, 
            otherNames=$otherNames, 
            personBirthdayString=$personBirthdayString, 
            personalizationDate=$personalizationDate, 
            personalizationDateString=$personalizationDateString, 
            personImage=${if (personImage != null) "Bitmap(present)" else "null"}, 
            personName=$personName, 
            personalNotes=$personalNotes, 
            stateOfBirth=$stateOfBirth, 
            profession=$profession, 
            publicKeyAlgorithm=$publicKeyAlgorithm, 
            releasingNation=$releasingNation, 
            releasingNationCode=$releasingNationCode, 
            serialNumber=$serialNumber, 
            signatureAlgorithm=$signatureAlgorithm, 
            signatureImage=${if (signatureImage != null) "Bitmap(present)" else "null"}, 
            signatureImageBase64=$signatureImageBase64, 
            state=$state, 
            systemSerialNumber=$systemSerialNumber, 
            taxId=$taxId, 
            taxOrExitRequirements=$taxOrExitRequirements, 
            telephone=$telephone, 
            title=$title, 
            validFrom=$validFrom, 
            validFromString=$validFromString, 
            validTo=$validTo, 
            validToString=$validToString, 
            sodBase64=$sodBase64, 
            dataGroupHashes=$dataGroupHashes,
            initialTimeStamp=$initialTimeStamp,
            elapsedTimeStamp=$elapsedTimeStamp,
            passiveAuthentication=$passiveAuthentication,
            activeAuthentication=$activeAuthentication,
            chipAuthentication=$chipAuthentication,
            isSuccess=$isSuccess
        )
    """.trimIndent()
}