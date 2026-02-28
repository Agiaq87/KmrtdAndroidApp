/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794

enum class EyeColourCode(override val code: Int) : EncodableEnum<EyeColourCode> {
    UNKNOWN(0),
    OTHER(1),
    BLACK(2),
    BLUE(3),
    BROWN(4),
    GREY(5),
    GREEN(6),
    HAZEL(7),
    MULTI_COLOURED(8),
    PINK(9);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): EyeColourCode? {
            return EncodableEnum.fromCode<EyeColourCode>(
                code,
                EyeColourCode::class.java
            )
        }
    }
}