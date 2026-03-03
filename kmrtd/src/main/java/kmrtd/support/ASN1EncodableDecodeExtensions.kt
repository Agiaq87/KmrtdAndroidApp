/*
 * Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.support

import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1Sequence
import org.bouncycastle.asn1.ASN1TaggedObject


/**
 * Converts an ASN1 sequence of tagged objects to a map.
 * Maps tag numbers to base objects.
 *
 * @param asn1Encodable an ASN1 sequence of tagged objects
 *
 * @return a map
 * @throws IllegalArgumentException if not a sequence and not a tagged object
 */
fun ASN1Encodable?.decodeTaggedObjects(): Map<Int, ASN1Encodable?> {
    return this?.let {
        when (this) {
            is ASN1Sequence -> {
                val asn1Sequence = ASN1Sequence.getInstance(this)

                buildMap {
                    asn1Sequence.forEach { encodable ->
                        if (encodable is ASN1TaggedObject) {
                            val asn1TaggedObject = ASN1TaggedObject.getInstance(encodable)
                            put(asn1TaggedObject.getTagNo(), asn1TaggedObject.baseObject)
                        }
                    }
                }
            }

            is ASN1TaggedObject -> {
                val asn1TaggedObject = ASN1TaggedObject.getInstance(this)
                mapOf(
                    asn1TaggedObject.tagNo to asn1TaggedObject.baseObject
                )
            }

            else -> throw IllegalArgumentException("Not a sequence and not a tagged object ${this.javaClass}")
        }
    } ?: run {
        mapOf()
    }
}

