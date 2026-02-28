/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794

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
            return EncodableEnum.fromCode<ImageColourSpaceCode>(
                code,
                ImageColourSpaceCode::class.java
            )
        }
    }
}