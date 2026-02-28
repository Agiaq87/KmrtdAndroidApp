/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794

import org.bouncycastle.asn1.ASN1Encodable
import kmrtd.ASN1Util
import java.math.BigInteger

data class ImageFaceMeasurementsBlock(
    val imageHeadWidth: BigInteger?,
    val imageInterEyeDistance: BigInteger?,
    val imageEyeToMouthDistance: BigInteger?,
    val imageHeadLength: BigInteger?
) : Block() {

    /*internal constructor(asn1Encodable: ASN1Encodable?) {
        val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)
        if (taggedObjects.containsKey(0)) {
            imageHeadWidth = ASN1Util.decodeBigInteger(taggedObjects.get(0))
        }
        if (taggedObjects.containsKey(1)) {
            imageInterEyeDistance = ASN1Util.decodeBigInteger(taggedObjects.get(1))
        }
        if (taggedObjects.containsKey(2)) {
            imageEyeToMouthDistance = ASN1Util.decodeBigInteger(taggedObjects.get(2))
        }
        if (taggedObjects.containsKey(3)) {
            imageHeadLength = ASN1Util.decodeBigInteger(taggedObjects.get(3))
        }
    }*/

    /*public override fun hashCode(): Int {
        return Objects.hash(
            imageEyeToMouthDistance,
            imageHeadLength,
            imageHeadWidth,
            imageInterEyeDistance
        )
    }

    public override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null) return false
        if (javaClass != obj.javaClass) return false
        val other = obj as ImageFaceMeasurementsBlock
        return imageEyeToMouthDistance == other.imageEyeToMouthDistance
                && imageHeadLength == other.imageHeadLength
                && imageHeadWidth == other.imageHeadWidth
                && imageInterEyeDistance == other.imageInterEyeDistance
    }

    override fun toString(): String {
        return ("ImageFaceMeasurementsBlock ["
                + "imageHeadWidth: " + imageHeadWidth
                + ", imageInterEyeDistance: " + imageInterEyeDistance
                + ", imageEyeToMouthDistance: " + imageEyeToMouthDistance
                + ", imageHeadLength: " + imageHeadLength
                + "]")
    }*/

    override val aSN1Object: ASN1Encodable
        get() = ASN1Util.encodeTaggedObjects(
            buildMap {
                imageHeadWidth?.let { put(0, ASN1Util.encodeBigInteger(it)) }
                imageInterEyeDistance?.let { put(1, ASN1Util.encodeBigInteger(it)) }
                imageEyeToMouthDistance?.let { put(2, ASN1Util.encodeBigInteger(it)) }
                imageHeadLength?.let { put(3, ASN1Util.encodeBigInteger(it)) }
            }
        )
    /* PACKAGE */
    /*get() {
        val taggedObjects: MutableMap<Int?, ASN1Encodable?> = HashMap<Int?, ASN1Encodable?>()
        if (imageHeadWidth != null) {
            taggedObjects.put(0, ASN1Util.encodeBigInteger(imageHeadWidth))
        }
        if (imageInterEyeDistance != null) {
            taggedObjects.put(1, ASN1Util.encodeBigInteger(imageInterEyeDistance))
        }
        if (imageEyeToMouthDistance != null) {
            taggedObjects.put(2, ASN1Util.encodeBigInteger(imageEyeToMouthDistance))
        }
        if (imageHeadLength != null) {
            taggedObjects.put(3, ASN1Util.encodeBigInteger(imageHeadLength))
        }
        return ASN1Util.encodeTaggedObjects(taggedObjects)
    }*/

    companion object {
        private const val serialVersionUID = -5665022845073986540L

        /**
         * Factory method
         */
        @JvmStatic
        fun from(asn1Encodable: ASN1Encodable?): ImageFaceMeasurementsBlock {
            val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)

            return ImageFaceMeasurementsBlock(
                imageHeadWidth = if (taggedObjects.containsKey(0)) ASN1Util.decodeBigInteger(
                    taggedObjects[0]
                ) else null,
                imageInterEyeDistance = if (taggedObjects.containsKey(1)) ASN1Util.decodeBigInteger(
                    taggedObjects[1]
                ) else null,
                imageEyeToMouthDistance = if (taggedObjects.containsKey(2)) ASN1Util.decodeBigInteger(
                    taggedObjects[2]
                ) else null,
                imageHeadLength = if (taggedObjects.containsKey(3)) ASN1Util.decodeBigInteger(
                    taggedObjects[3]
                ) else null
            )
        }
    }
}