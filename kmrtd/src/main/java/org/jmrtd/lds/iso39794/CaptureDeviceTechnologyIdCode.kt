/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package org.jmrtd.lds.iso39794

enum class CaptureDeviceTechnologyIdCode(override val code: Int) :
    EncodableEnum<CaptureDeviceTechnologyIdCode> {
    UNKNOWN_CAPTURE_DEVICE_TECHNOLOGY(0),
    OTHER_CAPTURE_DEVICE_TECHNOLOGY(1),
    SCANNED_INK_ON_PAPER(2),
    OPTICAL_TIR_BRIGHT_FIELD(3),
    OPTICAL_TIR_DARK_FIELD(4),
    OPTICAL_IMAGE(5),
    OPTICAL_LOW_FREQUENCY_3D_MAPPED(6),
    OPTICAL_HIGH_FREQUENCY_3D_MAPPED(7),
    CAPACITIVE(9),
    CAPACITIVE_RF(10),
    ELECTRO_LUMINESCENCE(11),
    REFLECTED_ULTRASONIC(12),
    IMPEDIOGRAPHIC_ULTRASONIC(13),
    THERMAL(14),
    DIRECT_PRESSURE(15),
    INDIRECT_PRESSURE(16),
    LIVE_TAPE(17),
    LATENT_IMPRESSION(18),
    LATENT_PHOTO(19),
    LATENT_MOLDED(20),
    LATENT_TRACING(21),
    LATENT_LIFT(22);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): CaptureDeviceTechnologyIdCode? {
            return EncodableEnum.fromCode<CaptureDeviceTechnologyIdCode>(
                code,
                CaptureDeviceTechnologyIdCode::class.java
            )
        }
    }
}