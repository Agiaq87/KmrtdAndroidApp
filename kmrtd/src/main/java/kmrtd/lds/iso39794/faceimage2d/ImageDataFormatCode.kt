package kmrtd.lds.iso39794.faceimage2d

import kmrtd.lds.iso39794.EncodableEnum

enum class ImageDataFormatCode(override val code: Int, val mimeType: String) :
    EncodableEnum<ImageDataFormatCode> {
    UNKNOWN(0, "image/raw"),
    JPEG(2, "image/jpeg"),
    JPEG2000_LOSSY(3, "image/jp2"),
    JPEG2000_LOSSLESS(4, "image/jp2");

    companion object {
        @JvmStatic
        fun fromCode(code: Int): ImageDataFormatCode? {
            return EncodableEnum.fromCode(
                code,
                ImageDataFormatCode::class.java
            )
        }

        @JvmStatic
        fun toMimeType(imageDataFormatCode: ImageDataFormatCode?): String =
            imageDataFormatCode?.mimeType ?: "image/raw"
    }
}