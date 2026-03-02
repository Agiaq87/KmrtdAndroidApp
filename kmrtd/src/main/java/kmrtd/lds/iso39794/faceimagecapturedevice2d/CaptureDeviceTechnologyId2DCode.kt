/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794.faceimagecapturedevice2d

import kmrtd.lds.iso39794.EncodableEnum

enum class CaptureDeviceTechnologyId2DCode(override val code: Int) :
    EncodableEnum<CaptureDeviceTechnologyId2DCode> {
    UNKNOWN(0),
    STATIC_PHOTOGRAPH_FROM_UNKNOWN_SOURCE(1),
    STATIC_PHOTOGRAPH_FROM_DIGITAL_STILL_IMAGE_CAMERA(2),
    STATIC_PHOTOGRAPH_FROM_SCANNER(3),
    VIDEO_FRAME_FROM_UNKNOWN_SOURCE(4),
    VIDEO_FRAME_FROM_ANALOGUE_VIDEO_CAMERA(5),
    VIDEO_FRAME_FROM_DIGITAL_VIDEO_CAMERA(6);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): CaptureDeviceTechnologyId2DCode? {
            return EncodableEnum.fromCode<CaptureDeviceTechnologyId2DCode>(
                code,
                CaptureDeviceTechnologyId2DCode::class.java
            )
        }
    }
}