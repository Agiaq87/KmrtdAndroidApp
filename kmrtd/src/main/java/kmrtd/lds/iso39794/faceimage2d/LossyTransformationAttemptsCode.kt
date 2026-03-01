package kmrtd.lds.iso39794.faceimage2d

import kmrtd.lds.iso39794.EncodableEnum

enum class LossyTransformationAttemptsCode(override val code: Int) :
    EncodableEnum<LossyTransformationAttemptsCode> {
    UNKNOWN(0),
    ZERO(1),
    ONE(2),
    MORE_THAN_ONE(3);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): LossyTransformationAttemptsCode? {
            return EncodableEnum.Companion.fromCode<LossyTransformationAttemptsCode>(
                code,
                LossyTransformationAttemptsCode::class.java
            )
        }
    }
}