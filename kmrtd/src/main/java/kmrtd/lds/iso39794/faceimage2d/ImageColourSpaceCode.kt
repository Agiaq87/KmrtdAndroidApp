package kmrtd.lds.iso39794.faceimage2d

import kmrtd.lds.iso39794.EncodableEnum

enum class ImageColourSpaceCode(override val code: Int) :
    EncodableEnum<ImageColourSpaceCode> {
    UNKNOWN(0),
    OTHER(1),
    RGB_24BIT(2),
    RGB_48BIT(3),
    YUV_422(4),
    GREYSCALE_8BIT(5),
    GREYSCALE_16BIT(6);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): ImageColourSpaceCode? {
            return EncodableEnum.Companion.fromCode<ImageColourSpaceCode>(
                code,
                ImageColourSpaceCode::class.java
            )
        }
    }
}