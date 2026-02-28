/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package org.jmrtd.lds.iso39794

enum class VerticalOrientationCode(override val code: Int) : EncodableEnum<VerticalOrientationCode> {
    UNDEFINED(0),
    TOP_TO_BOTTOM(1),
    BOTTOM_TO_TOP(2);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): VerticalOrientationCode? {
            return EncodableEnum.fromCode<VerticalOrientationCode>(
                code,
                VerticalOrientationCode::class.java
            )
        }
    }
}