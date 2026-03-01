package kmrtd.lds.iso39794.irisimage

import kmrtd.lds.iso39794.EncodableEnum

enum class CompressionHistoryCode(override val code: Int) : EncodableEnum<CompressionHistoryCode> {
    UNDEFINED(0),
    LOSSLESS_OR_NONE(1),
    LOSSY(2);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): CompressionHistoryCode? {
            return EncodableEnum.Companion.fromCode<CompressionHistoryCode>(
                code,
                CompressionHistoryCode::class.java
            )
        }
    }
}