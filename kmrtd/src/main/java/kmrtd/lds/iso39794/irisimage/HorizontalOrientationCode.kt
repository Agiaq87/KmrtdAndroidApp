package kmrtd.lds.iso39794.irisimage

import kmrtd.lds.iso39794.EncodableEnum

enum class HorizontalOrientationCode(override val code: Int) :
    EncodableEnum<HorizontalOrientationCode> {
    UNDEFINED(0),
    LEFT_TO_RIGHT(1),
    RIGHT_TO_LEFT(2);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): HorizontalOrientationCode? {
            return EncodableEnum.Companion.fromCode<HorizontalOrientationCode>(
                code,
                HorizontalOrientationCode::class.java
            )
        }
    }
}