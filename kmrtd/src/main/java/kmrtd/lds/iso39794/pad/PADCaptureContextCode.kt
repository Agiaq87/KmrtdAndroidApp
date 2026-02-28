/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794.pad

import org.kmrtd.lds.iso39794.EncodableEnum

enum class PADCaptureContextCode(override val code: Int) : EncodableEnum<PADCaptureContextCode> {
    ENROLMENT(0),
    VERIFICATION(1),
    IDENTIFICATION(2);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): PADCaptureContextCode? {
            return EncodableEnum.fromCode<PADCaptureContextCode>(
                code,
                PADCaptureContextCode::class.java
            )
        }
    }
}