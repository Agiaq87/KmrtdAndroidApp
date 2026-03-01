package kmrtd.lds.iso39794.irisimage

import kmrtd.lds.iso39794.EncodableEnum

enum class RangingErrorCode(override val code: Int) : EncodableEnum<RangingErrorCode> {
    UNASSIGNED(0),
    FAILED(1),
    OVERFLOW(2);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): RangingErrorCode? {
            return EncodableEnum.Companion.fromCode<RangingErrorCode>(
                code,
                RangingErrorCode::class.java
            )
        }
    }
}