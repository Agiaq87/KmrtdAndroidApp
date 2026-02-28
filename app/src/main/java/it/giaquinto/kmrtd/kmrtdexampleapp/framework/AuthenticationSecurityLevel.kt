package it.giaquinto.kmrtd.kmrtdexampleapp.framework

enum class AuthenticationSecurityLevel {
    UNKNOWN,

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
    PA_ONLY, // Cannot say if document is cloned or not

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
    AA,

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
    EACCA,
}