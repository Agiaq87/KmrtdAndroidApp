/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso19794.support

/**
 * Face image type code based on Section 5.7.1 of ISO 19794-5.
 *
 * [see](https://www.iso.org/obp/ui/es/#iso:std:iso-iec:19794:-5:ed-2:v1:en)
 * [see](https://egovstandards.gov.in/sites/default/files/Face_Image_Data_Standard_Ver1.0.pdf)
 * @author The JMRTD team (info@jmrtd.org)
 * @author Alessandro Giaquinto (agiaquinto87@gmail.com)
 * @version $Revision: 1808 $
 */
enum class FaceImageType(private val code: Int) {
    BASIC(FaceImageInfoConstants.FACE_IMAGE_TYPE_BASIC),
    FULL_FRONTAL(FaceImageInfoConstants.FACE_IMAGE_TYPE_FULL_FRONTAL),
    TOKEN_FRONTAL(FaceImageInfoConstants.FACE_IMAGE_TYPE_TOKEN_FRONTAL);

    fun toInt(): Int =
        code

    companion object {
        @JvmStatic
        fun toFaceImageType(code: Int): FaceImageType =
            when (code) {
                FaceImageInfoConstants.FACE_IMAGE_TYPE_BASIC -> BASIC
                FaceImageInfoConstants.FACE_IMAGE_TYPE_FULL_FRONTAL -> FULL_FRONTAL
                FaceImageInfoConstants.FACE_IMAGE_TYPE_TOKEN_FRONTAL -> TOKEN_FRONTAL
                else -> throw IllegalArgumentException("Unknown face image type code: $code")
            }
    }
}