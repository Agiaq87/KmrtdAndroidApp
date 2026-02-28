/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package org.jmrtd.lds.iso39794

enum class HorizontalOrientationCode(override val code: Int) :
    EncodableEnum<HorizontalOrientationCode> {
    UNDEFINED(0),
    LEFT_TO_RIGHT(1),
    RIGHT_TO_LEFT(2);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): HorizontalOrientationCode? {
            return EncodableEnum.fromCode<HorizontalOrientationCode>(
                code,
                HorizontalOrientationCode::class.java
            )
        }
    }
}