/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package org.jmrtd.lds.iso39794

enum class RangingErrorCode(override val code: Int) : EncodableEnum<RangingErrorCode> {
    UNASSIGNED(0),
    FAILED(1),
    OVERFLOW(2);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): RangingErrorCode? {
            return EncodableEnum.fromCode<RangingErrorCode>(code, RangingErrorCode::class.java)
        }
    }
}