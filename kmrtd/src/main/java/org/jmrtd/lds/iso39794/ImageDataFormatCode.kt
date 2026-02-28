/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package org.jmrtd.lds.iso39794

enum class ImageDataFormatCode(override val code: Int, val mimeType: String) : EncodableEnum<ImageDataFormatCode> {
    UNKNOWN(0, "image/raw"),
    JPEG(2, "image/jpeg"),
    JPEG2000_LOSSY(3, "image/jp2"),
    JPEG2000_LOSSLESS(4, "image/jp2");

    companion object {
        @JvmStatic
        fun fromCode(code: Int): ImageDataFormatCode? {
            return EncodableEnum.fromCode<ImageDataFormatCode>(
                code,
                ImageDataFormatCode::class.java
            )
        }

        @JvmStatic
        fun toMimeType(imageDataFormatCode: ImageDataFormatCode?): String =
            imageDataFormatCode?.mimeType ?: "image/raw"
    }
}