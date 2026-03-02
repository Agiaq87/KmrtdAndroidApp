/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794.fingerimage

import kmrtd.lds.iso39794.EncodableEnum

enum class AnnotationReasonCode(override val code: Int) : EncodableEnum<AnnotationReasonCode> {
    UNKNOWN(0),
    OTHER(1),
    AMPUTATED(2),
    UNABLE_TO_PRINT(3),
    BANDAGED(4),
    PHYSICALLY_CHALLENGED(5),
    DISEASED(6);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): AnnotationReasonCode? {
            return EncodableEnum.fromCode<AnnotationReasonCode>(
                code,
                AnnotationReasonCode::class.java
            )
        }
    }
}