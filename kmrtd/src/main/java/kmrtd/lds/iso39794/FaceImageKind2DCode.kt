/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794

enum class FaceImageKind2DCode(override val code: Int) : EncodableEnum<FaceImageKind2DCode> {
    MRTD(0),
    GENERAL_PURPOSE(1);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): FaceImageKind2DCode? {
            return EncodableEnum.fromCode<FaceImageKind2DCode>(
                code,
                FaceImageKind2DCode::class.java
            )
        }
    }
}