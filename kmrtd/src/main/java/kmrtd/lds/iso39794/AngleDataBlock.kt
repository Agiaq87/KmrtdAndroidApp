/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794

import org.bouncycastle.asn1.ASN1Encodable
import kmrtd.ASN1Util

data class AngleDataBlock(
    /** INTEGER (-180..180).  */
    val angleValue: Int,
    /** INTEGER (0..180).  */
    val angleUncertainty: Int?
) : Block() {
    /** INTEGER (-180..180).  *//*
    val angleValue: Int

    *//** INTEGER (0..180).  *//*
    var angleUncertainty: Int = 0
        private set

    constructor(angleValue: Int, angleUncertainty: Int) {
        this.angleValue = angleValue
        this.angleUncertainty = angleUncertainty
    }*/

    /*internal constructor(asn1Encodable: ASN1Encodable?) {
        val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)
        angleValue = ASN1Util.decodeInt(taggedObjects.get(0))
        if (taggedObjects.containsKey(1)) {
            angleUncertainty = ASN1Util.decodeInt(taggedObjects.get(1))
        } else {
            angleUncertainty = -1
        }
    }*/

    /*public override fun hashCode(): Int {
        return Objects.hash(angleUncertainty, angleValue)
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

        val other = obj as AngleDataBlock
        return angleUncertainty == other.angleUncertainty && angleValue == other.angleValue
    }

    override fun toString(): String {
        return ("AngleDataBlock ["
                + "angleValue: " + angleValue
                + ", angleUncertainty: " + angleUncertainty
                + "]")
    }*/

    override val aSN1Object: ASN1Encodable
        get() = ASN1Util.encodeTaggedObjects(
            buildMap {
                put(0, ASN1Util.encodeInt(angleValue))
                angleUncertainty?.let {
                    if (it >= 0) {
                        put(1, ASN1Util.encodeInt(it))
                    }
                }
            }
        )
        /*get() {
            val taggedObjects: MutableMap<Int?, ASN1Encodable?> = HashMap<Int?, ASN1Encodable?>()
            taggedObjects[0] = ASN1Util.encodeInt(angleValue)
            if (angleUncertainty >= 0) {
                taggedObjects[1] = ASN1Util.encodeInt(angleUncertainty)
            }
            return ASN1Util.encodeTaggedObjects(taggedObjects)
        }*/

    companion object {
        private const val serialVersionUID = 3589963464356857977L

        /**
         * Factory method
         */
        @JvmStatic
        fun from(asn1Encodable: ASN1Encodable?): AngleDataBlock {
            val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)

            return AngleDataBlock(
                angleValue = ASN1Util.decodeInt(taggedObjects[0]),
                angleUncertainty = if (taggedObjects.containsKey(1)) ASN1Util.decodeInt(taggedObjects[1]) else -1
            )
        }
    }
}
