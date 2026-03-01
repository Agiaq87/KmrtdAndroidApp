package kmrtd.lds.iso39794.irisimage

import kmrtd.lds.iso39794.EncodableEnum

enum class VerticalOrientationCode(override val code: Int) :
    EncodableEnum<VerticalOrientationCode> {
    UNDEFINED(0),
    TOP_TO_BOTTOM(1),
    BOTTOM_TO_TOP(2);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): VerticalOrientationCode? {
            return EncodableEnum.Companion.fromCode<VerticalOrientationCode>(
                code,
                VerticalOrientationCode::class.java
            )
        }
    }
}