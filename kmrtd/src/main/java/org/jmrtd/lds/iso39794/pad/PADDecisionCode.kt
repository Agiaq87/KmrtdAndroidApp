/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package org.jmrtd.lds.iso39794.pad

import org.jmrtd.lds.iso39794.EncodableEnum

enum class PADDecisionCode(override val code: Int) : EncodableEnum<PADDecisionCode> {
    NO_ATTACK(0),
    ATTACK(1),
    FAILURE_TO_ASSESS(2);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): PADDecisionCode? {
            return EncodableEnum.fromCode<PADDecisionCode>(code, PADDecisionCode::class.java)
        }
    }
}