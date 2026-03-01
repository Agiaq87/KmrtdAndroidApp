/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso19794.support

import kmrtd.support.StandardRequires

/**
 * Source type based on Section 5.7.6 of ISO 19794-5.
 *
 * [see](https://www.iso.org/obp/ui/es/#iso:std:iso-iec:19794:-5:ed-2:v1:en)
 * [see](https://egovstandards.gov.in/sites/default/files/Face_Image_Data_Standard_Ver1.0.pdf)
 * @author The JMRTD team (info@jmrtd.org)
 * @author Alessandro Giaquinto (agiaquinto87@gmail.com)
 * @version 2.0
 */
enum class SourceType(val code: Int) : StandardRequires<SourceType> {
    UNSPECIFIED(FaceImageInfoConstants.SOURCE_TYPE_UNSPECIFIED),
    STATIC_PHOTO_UNKNOWN_SOURCE(FaceImageInfoConstants.SOURCE_TYPE_STATIC_PHOTO_UNKNOWN_SOURCE),
    STATIC_PHOTO_DIGITAL_CAM(FaceImageInfoConstants.SOURCE_TYPE_STATIC_PHOTO_DIGITAL_CAM),
    STATIC_PHOTO_SCANNER(FaceImageInfoConstants.SOURCE_TYPE_STATIC_PHOTO_SCANNER),
    VIDEO_FRAME_UNKNOWN_SOURCE(FaceImageInfoConstants.SOURCE_TYPE_VIDEO_FRAME_UNKNOWN_SOURCE),
    VIDEO_FRAME_ANALOG_CAM(FaceImageInfoConstants.SOURCE_TYPE_VIDEO_FRAME_ANALOG_CAM),
    VIDEO_FRAME_DIGITAL_CAM(FaceImageInfoConstants.SOURCE_TYPE_VIDEO_FRAME_DIGITAL_CAM),
    UNKNOWN(FaceImageInfoConstants.SOURCE_TYPE_UNKNOWN);

    override fun standardRequires(): List<SourceType> =
        listOf(
            SourceType.STATIC_PHOTO_DIGITAL_CAM,
            SourceType.STATIC_PHOTO_SCANNER,
        )

    companion object {
        @JvmStatic
        fun toSourceType(code: Int): SourceType? =
            when (code) {
                FaceImageInfoConstants.SOURCE_TYPE_UNSPECIFIED -> UNSPECIFIED
                FaceImageInfoConstants.SOURCE_TYPE_STATIC_PHOTO_UNKNOWN_SOURCE -> STATIC_PHOTO_UNKNOWN_SOURCE
                FaceImageInfoConstants.SOURCE_TYPE_STATIC_PHOTO_DIGITAL_CAM -> STATIC_PHOTO_DIGITAL_CAM
                FaceImageInfoConstants.SOURCE_TYPE_STATIC_PHOTO_SCANNER -> STATIC_PHOTO_SCANNER
                FaceImageInfoConstants.SOURCE_TYPE_VIDEO_FRAME_UNKNOWN_SOURCE -> VIDEO_FRAME_UNKNOWN_SOURCE
                FaceImageInfoConstants.SOURCE_TYPE_VIDEO_FRAME_ANALOG_CAM -> VIDEO_FRAME_ANALOG_CAM
                FaceImageInfoConstants.SOURCE_TYPE_VIDEO_FRAME_DIGITAL_CAM -> VIDEO_FRAME_DIGITAL_CAM
                FaceImageInfoConstants.SOURCE_TYPE_UNKNOWN -> UNKNOWN
                else -> null
            }
    }
}