/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794.pad

import org.kmrtd.lds.iso39794.EncodableEnum

enum class PADSupervisionLevelCode(override val code: Int) : EncodableEnum<PADSupervisionLevelCode> {
    UNKNOWN(0),
    CONTROLLED(1),
    ASSISTED(2),
    OBSERVED(3),
    UNATTENDED(4);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): PADSupervisionLevelCode? {
            return EncodableEnum.fromCode<PADSupervisionLevelCode>(
                code,
                PADSupervisionLevelCode::class.java
            )
        }
    }
}