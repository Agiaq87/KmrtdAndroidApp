/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794.pad

import kmrtd.lds.iso39794.EncodableEnum

enum class PADCriteriaCategoryCode(override val code: Int) : EncodableEnum<PADCriteriaCategoryCode> {
    UNKNOWN(0),
    INDIVIDUAL(1),
    COMMON(2);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): PADCriteriaCategoryCode? {
            return EncodableEnum.fromCode<PADCriteriaCategoryCode>(
                code,
                PADCriteriaCategoryCode::class.java
            )
        }
    }
}