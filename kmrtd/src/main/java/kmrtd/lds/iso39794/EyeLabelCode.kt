/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794

enum class EyeLabelCode(override val code: Int) : EncodableEnum<EyeLabelCode> {
    UNKNOWN(0),
    RIGHT_IRIS(1),
    LEFT_IRIS(2);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): EyeLabelCode? {
            return EncodableEnum.fromCode<EyeLabelCode>(code, EyeLabelCode::class.java)
        }
    }
}