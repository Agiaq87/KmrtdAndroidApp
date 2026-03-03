/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.support

import javax.crypto.BadPaddingException

/**
 * Unpads the input `bytes` according to ISO9797-1 padding method 2.
 *
 * @param bytes the input
 *
 * @return the unpadded bytes
 *
 * @throws BadPaddingException on padding exception
 */
@Throws(BadPaddingException::class)
fun ByteArray.unpad(): ByteArray {
    var i = this.size - 1
    while (i >= 0 && this[i].toInt() == 0x00) {
        i--
    }
    if ((this[i].toInt() and 0xFF) != 0x80) {
        throw BadPaddingException("Expected constant 0x80, found 0x" + Integer.toHexString((this[i].toInt() and 0x000000FF)))
    }
    val out = ByteArray(i)
    System.arraycopy(this, 0, out, 0, i)
    return out
}