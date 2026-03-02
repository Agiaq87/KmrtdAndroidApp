/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794.fingerimage

import kmrtd.lds.iso39794.EncodableEnum

enum class ImageDataFormatCode(override val code: Int, mimeType: String) :
    EncodableEnum<ImageDataFormatCode> {
    PGM(0, "image/pgm"),
    WSQ(1, "image/x-wsq"),
    JPEG2000_LOSSY(2, "image/jp2"),
    JPEG2000_LOSSLESS(3, "image/jp2"),
    PNG(4, "image/png");

    val mimeType: String? = null

    companion object {
        @JvmStatic
        fun fromCode(code: Int): ImageDataFormatCode? {
            return EncodableEnum.fromCode<ImageDataFormatCode>(
                code,
                ImageDataFormatCode::class.java
            )
        }
    }
}