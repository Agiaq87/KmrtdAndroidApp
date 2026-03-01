/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso19794.support

import kmrtd.support.StandardRequires

/**
 * Face image type code based on Section 5.7.1 of ISO 19794-5.
 *
 * [see](https://www.iso.org/obp/ui/es/#iso:std:iso-iec:19794:-5:ed-2:v1:en)
 * [see](https://egovstandards.gov.in/sites/default/files/Face_Image_Data_Standard_Ver1.0.pdf)
 * @author The JMRTD team (info@jmrtd.org)
 * @author Alessandro Giaquinto (agiaquinto87@gmail.com)
 * @version 2.0
 */
enum class FaceImageType(val code: Int) : StandardRequires<FaceImageType> {
    BASIC(FaceImageInfoConstants.FACE_IMAGE_TYPE_BASIC),
    FULL_FRONTAL(FaceImageInfoConstants.FACE_IMAGE_TYPE_FULL_FRONTAL),
    TOKEN_FRONTAL(FaceImageInfoConstants.FACE_IMAGE_TYPE_TOKEN_FRONTAL);

    override fun standardRequires(): List<FaceImageType> =
        listOf(FaceImageType.FULL_FRONTAL)

    companion object {
        @JvmStatic
        fun toFaceImageType(code: Int): FaceImageType? =
            when (code) {
                FaceImageInfoConstants.FACE_IMAGE_TYPE_BASIC -> BASIC
                FaceImageInfoConstants.FACE_IMAGE_TYPE_FULL_FRONTAL -> FULL_FRONTAL
                FaceImageInfoConstants.FACE_IMAGE_TYPE_TOKEN_FRONTAL -> TOKEN_FRONTAL
                else -> null
            }
    }
}