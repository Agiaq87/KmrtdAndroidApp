/*
 * Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.support

import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.DEROctetString
import org.bouncycastle.asn1.DERSequence
import org.bouncycastle.asn1.DERTaggedObject
import java.math.BigInteger

/**
 * Boolean
 */
fun Boolean.encode(): ASN1Encodable =
    DEROctetString(byteArrayOf((if (this) 0xFF else 0x00).toByte()))

/**
 * ByteArray
 */
fun ByteArray.encode(): ASN1Encodable =
    DEROctetString(this)


/**
 * Int
 */
fun BigInteger.encode(): ASN1Encodable =
    DEROctetString(toByteArray())

fun Int.encode(): ASN1Encodable =
    DEROctetString(BigInteger.valueOf(toLong()).toByteArray())

/**
 * Map
 */
fun Map<Int, ASN1Encodable>.encode(): ASN1Encodable =
    DERSequence(
        this.map {
            DERTaggedObject(
                false,
                it.key,
                it.value
            )
        }.toTypedArray<ASN1Encodable>()
    )

fun MutableMap<Int, ASN1Encodable>.encode(): ASN1Encodable =
    DERSequence(
        this.map {
            DERTaggedObject(
                false,
                it.key,
                it.value
            )
        }.toTypedArray<ASN1Encodable>()
    )