/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso19794.support

import kmrtd.support.StandardRequires

/**
 * Color space code based on Section 5.7.4 of ISO 19794-5.
 *
 *
 * [see](https://www.iso.org/obp/ui/es/#iso:std:iso-iec:19794:-5:ed-2:v1:en)
 * [see](https://egovstandards.gov.in/sites/default/files/Face_Image_Data_Standard_Ver1.0.pdf)
 * @author The JMRTD team (info@jmrtd.org)
 * @author Alessandro Giaquinto (agiaquinto87@gmail.com)
 * @version 2.0
 */
enum class ImageColorSpace(val code: Int) : StandardRequires<ImageColorSpace> {
    UNSPECIFIED(FaceImageInfoConstants.IMAGE_COLOR_SPACE_UNSPECIFIED),
    STANDARD_REQUIRED_RGB24(FaceImageInfoConstants.IMAGE_COLOR_SPACE_RGB24_STANDARD_REQUIRED),
    YUV422(FaceImageInfoConstants.IMAGE_COLOR_SPACE_YUV422),
    GRAY8(FaceImageInfoConstants.IMAGE_COLOR_SPACE_GRAY8),
    OTHER(FaceImageInfoConstants.IMAGE_COLOR_SPACE_OTHER),
    UNKNOWN(FaceImageInfoConstants.IMAGE_COLOR_SPACE_UNKNOWN);

    override fun standardRequires(): List<ImageColorSpace> =
        listOf(ImageColorSpace.STANDARD_REQUIRED_RGB24)

    companion object {
        @JvmStatic
        fun toImageColorSpace(code: Int): ImageColorSpace? =
            when (code) {
                FaceImageInfoConstants.IMAGE_COLOR_SPACE_UNSPECIFIED -> UNSPECIFIED
                FaceImageInfoConstants.IMAGE_COLOR_SPACE_RGB24_STANDARD_REQUIRED -> STANDARD_REQUIRED_RGB24
                FaceImageInfoConstants.IMAGE_COLOR_SPACE_YUV422 -> YUV422
                FaceImageInfoConstants.IMAGE_COLOR_SPACE_GRAY8 -> GRAY8
                FaceImageInfoConstants.IMAGE_COLOR_SPACE_OTHER -> OTHER
                FaceImageInfoConstants.IMAGE_COLOR_SPACE_UNKNOWN -> UNKNOWN
                else -> null
            }
    }
}