/*
 * Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.support

import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1InputStream
import java.io.IOException
import java.io.InputStream

@Throws(IOException::class)
fun InputStream?.readASN1Object(): ASN1Encodable? =
    ASN1InputStream(this, true).readObject()