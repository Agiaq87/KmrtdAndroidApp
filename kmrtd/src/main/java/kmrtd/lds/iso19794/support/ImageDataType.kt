/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso19794.support

import kmrtd.support.StandardRequires

/**
 * Image data type code based on Section 5.7.2 of ISO 19794-5.
 *
 * [see](https://www.iso.org/obp/ui/es/#iso:std:iso-iec:19794:-5:ed-2:v1:en)
 * [see](https://egovstandards.gov.in/sites/default/files/Face_Image_Data_Standard_Ver1.0.pdf)
 * @author The JMRTD team (info@jmrtd.org)
 * @author Alessandro Giaquinto (agiaquinto87@gmail.com)
 * @version 2.0
 */
enum class ImageDataType(val code: Int) : StandardRequires<ImageDataType> {
    TYPE_UNCOMPRESSED_NO_BIT_PACKING(FaceImageInfoConstants.IMAGE_DATA_TYPE_UNCOMPRESSED_NO_BIT_PACKING),
    TYPE_UNCOMPRESSED_BIT_PACKED(FaceImageInfoConstants.IMAGE_DATA_TYPE_UNCOMPRESSED_BIT_PACKING),
    TYPE_JPEG2000(FaceImageInfoConstants.IMAGE_DATA_TYPE_JPEG2000),
    TYPE_PNG(FaceImageInfoConstants.IMAGE_DATAT_TYPE_PNG);

    override fun standardRequires(): List<ImageDataType> =
        listOf(ImageDataType.TYPE_JPEG2000)

    companion object {
        @JvmStatic
        fun toImageDataType(code: Int): ImageDataType? =
            when (code) {
                FaceImageInfoConstants.IMAGE_DATA_TYPE_UNCOMPRESSED_NO_BIT_PACKING -> TYPE_UNCOMPRESSED_NO_BIT_PACKING
                FaceImageInfoConstants.IMAGE_DATA_TYPE_UNCOMPRESSED_BIT_PACKING -> TYPE_UNCOMPRESSED_BIT_PACKED
                FaceImageInfoConstants.IMAGE_DATA_TYPE_JPEG2000 -> TYPE_JPEG2000
                FaceImageInfoConstants.IMAGE_DATAT_TYPE_PNG -> TYPE_PNG
                else -> null
            }
    }
}