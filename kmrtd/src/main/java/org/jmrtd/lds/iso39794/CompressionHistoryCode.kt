/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package org.jmrtd.lds.iso39794

enum class CompressionHistoryCode(override val code: Int) : EncodableEnum<CompressionHistoryCode> {
    UNDEFINED(0),
    LOSSLESS_OR_NONE(1),
    LOSSY(2);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): CompressionHistoryCode? {
            return EncodableEnum.fromCode<CompressionHistoryCode>(
                code,
                CompressionHistoryCode::class.java
            )
        }
    }
}