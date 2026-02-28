/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794

import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1Sequence
import kmrtd.ASN1Util

data class ImageSizeBlock(
    val width: Int?,
    val height: Int?
) : Block() {
    /*constructor(asn1Encodable: ASN1Encodable?) {
        require(asn1Encodable is ASN1Sequence) { "Cannot decode!" }

        val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)
        width = ASN1Util.decodeInt(taggedObjects[0])
        height = ASN1Util.decodeInt(taggedObjects[1])
    }*/

  /*  public override fun hashCode(): Int {
        return Objects.hash(height, width)
    }

    public override fun equals(obj: Any?): Boolean {
        if (this === obj) {
            return true
        }
        if (obj == null) {
            return false
        }
        if (javaClass != obj.javaClass) {
            return false
        }

        val other = obj as ImageSizeBlock
        return height == other.height && width == other.width
    }

    override fun toString(): String {
        return "ImageSizeBlock [width: $width, height: $height]"
    }*/

    override val aSN1Object: ASN1Encodable
        get() = ASN1Util.encodeTaggedObjects(
            buildMap {
                width?.let { put(0, ASN1Util.encodeInt(width)) }
                height?.let { put(1, ASN1Util.encodeInt(height)) }
            }
        )
        /*get() {
            val taggedObjects: MutableMap<Int?, ASN1Encodable?> = HashMap<Int?, ASN1Encodable?>()
            taggedObjects[0] = ASN1Util.encodeInt(width)
            taggedObjects[1] = ASN1Util.encodeInt(height)
            return ASN1Util.encodeTaggedObjects(taggedObjects)
        }*/

    companion object {
        private const val serialVersionUID = -261040653361008230L

        /**
         * Factory method
         */
        @JvmStatic
        fun from(asn1Encodable: ASN1Encodable?): ImageSizeBlock {
            require(asn1Encodable is ASN1Sequence) { "Cannot decode!" }

            val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)

            return ImageSizeBlock(
                ASN1Util.decodeInt(taggedObjects[0]),
                ASN1Util.decodeInt(taggedObjects[1])
            )
        }
    }
}
