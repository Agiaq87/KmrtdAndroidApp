/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794

import kmrtd.ASN1Util
import kmrtd.support.encode
import org.bouncycastle.asn1.ASN1Encodable

data class RollAngleBlock(
    val angle: Int,
    val uncertainty: Int
) : Block() {

    /*internal constructor(asn1Encodable: ASN1Encodable?) {
        val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)
        angle = ASN1Util.decodeInt(taggedObjects[0])
        uncertainty = ASN1Util.decodeInt(taggedObjects[1])
    }*/

    /*public override fun hashCode(): Int {
        return Objects.hash(angle, uncertainty)
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

        val other = obj as RollAngleBlock
        return angle == other.angle && uncertainty == other.uncertainty
    }*/

    override val aSN1Object: ASN1Encodable
        get() = mapOf(
            0 to angle.encode(),
            1 to uncertainty.encode()
        ).encode()
/*        get() {
            val taggedObjects: MutableMap<Int?, ASN1Encodable?> = HashMap<Int?, ASN1Encodable?>()
            taggedObjects.put(0, ASN1Util.encodeInt(angle))
            taggedObjects.put(1, ASN1Util.encodeInt(uncertainty))
            return ASN1Util.encodeTaggedObjects(taggedObjects)
        }*/

    companion object {
        private const val serialVersionUID = -1867300334704286030L

        /**
         * Factory method
         */
        @JvmStatic
        fun from(asn1Encodable: ASN1Encodable?): RollAngleBlock {
            val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)

            return RollAngleBlock(
                angle = ASN1Util.decodeInt(taggedObjects[0]),
                uncertainty = ASN1Util.decodeInt(taggedObjects[1])
            )
        }
    }
}
