/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794

enum class GenderCode(override val code: Int) : EncodableEnum<GenderCode> {
    UNKNOWN(0),
    OTHER(1),
    MALE(2),
    FEMALE(3);


    companion object {
        @JvmStatic
        fun fromCode(code: Int): GenderCode? {
            return EncodableEnum.fromCode<GenderCode>(
                code,
                GenderCode::class.java
            )
        }
    }
}