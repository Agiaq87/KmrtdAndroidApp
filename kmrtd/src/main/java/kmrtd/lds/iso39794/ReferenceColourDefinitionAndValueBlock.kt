/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794

import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1OctetString
import org.bouncycastle.asn1.DEROctetString
import org.kmrtd.ASN1Util

data class ReferenceColourDefinitionAndValueBlock(
    val referenceColourDefinition: ByteArray?,
    val referenceColourValue: ByteArray?
) : Block() {

    /*public override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + referenceColourDefinition.contentHashCode()
        result = prime * result + referenceColourValue.contentHashCode()
        return result
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

        val other = obj as ReferenceColourDefinitionAndValueBlock
        return referenceColourDefinition.contentEquals(other.referenceColourDefinition) && referenceColourValue.contentEquals(
            other.referenceColourValue
        )
    }

    override fun toString(): String {
        return ("ReferenceColourDefinitionAndValueBlock ["
                + "referenceColourDefinition: " + Hex.bytesToHexString(referenceColourDefinition)
                + ", referenceColourValue: " + Hex.bytesToHexString(referenceColourValue)
                + "]")
    }*/

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReferenceColourDefinitionAndValueBlock

        if (!referenceColourDefinition.contentEquals(other.referenceColourDefinition)) return false
        if (!referenceColourValue.contentEquals(other.referenceColourValue)) return false
        if (aSN1Object != other.aSN1Object) return false

        return true
    }

    override fun hashCode(): Int {
        var result = referenceColourDefinition?.contentHashCode() ?: 0
        result = 31 * result + (referenceColourValue?.contentHashCode() ?: 0)
        result = 31 * result + aSN1Object.hashCode()
        return result
    }

    override val aSN1Object: ASN1Encodable
        get() = ASN1Util.encodeTaggedObjects(
            buildMap {
                referenceColourDefinition?.let{
                    put(0, DEROctetString(it))
                }
                referenceColourValue?.let {
                    put(1, DEROctetString(it))
                }
            }
        )
        /*get() {
            val taggedObjects: MutableMap<Int?, ASN1Encodable?> = HashMap<Int?, ASN1Encodable?>()
            if (referenceColourDefinition != null) {
                taggedObjects.put(0, DEROctetString(referenceColourDefinition))
            }
            if (referenceColourValue != null) {
                taggedObjects.put(1, DEROctetString(referenceColourValue))
            }
            return ASN1Util.encodeTaggedObjects(taggedObjects)
        }*/

    companion object {
        private const val serialVersionUID = -7927429988191532374L

        /* PACKAGE */
        @JvmStatic
        fun decodeReferenceColourDefinitionAndValueBlocks(asn1Encodable: ASN1Encodable?): List<ReferenceColourDefinitionAndValueBlock> =
            if (ASN1Util.isSequenceOfSequences(asn1Encodable)) {
                ASN1Util.list(asn1Encodable).map { from(it) }
            } else {
                listOf(from(asn1Encodable))
            }


        /*{
            if (ASN1Util.isSequenceOfSequences(asn1Encodable)) {
                val blockASN1Objects = ASN1Util.list(asn1Encodable)
                val blocks: MutableList<ReferenceColourDefinitionAndValueBlock?> = mutableListOf()
                for (blockASN1Object in blockASN1Objects) {
                    blocks.add(ReferenceColourDefinitionAndValueBlock(blockASN1Object))
                }
                return blocks
            } else {
                return listOf<ReferenceColourDefinitionAndValueBlock>(
                    ReferenceColourDefinitionAndValueBlock(asn1Encodable)
                )
            }
        }*/

        /**
         * Factory method
         *
         * ReferenceColourDefinitionAndValueBlock ::= SEQUENCE {
         *         referenceColourDefinition [0] OCTET STRING OPTIONAL,
         *         referenceColourValue [1] OCTET STRING OPTIONAL,
         *         ...
         * }
         */
        @JvmStatic
        fun from(asn1Encodable: ASN1Encodable?): ReferenceColourDefinitionAndValueBlock {
            val taggedObjects = ASN1Util.decodeTaggedObjects(asn1Encodable)
            return ReferenceColourDefinitionAndValueBlock(
                referenceColourDefinition = if (taggedObjects.containsKey(0)) ASN1OctetString.getInstance(taggedObjects[0]).octets else null,
                referenceColourValue = if (taggedObjects.containsKey(1)) ASN1OctetString.getInstance(taggedObjects[1]).octets else null
            )
        }
    }
}
