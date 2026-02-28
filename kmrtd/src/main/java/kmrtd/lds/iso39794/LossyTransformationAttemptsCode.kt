/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794

enum class LossyTransformationAttemptsCode(override val code: Int) :
    EncodableEnum<LossyTransformationAttemptsCode> {
    UNKNOWN(0),
    ZERO(1),
    ONE(2),
    MORE_THAN_ONE(3);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): LossyTransformationAttemptsCode? {
            return EncodableEnum.fromCode<LossyTransformationAttemptsCode>(
                code,
                LossyTransformationAttemptsCode::class.java
            )
        }
    }
}