package kmrtd.lds.iso39794.irisimage

import kmrtd.lds.iso39794.EncodableEnum

enum class EyeLabelCode(override val code: Int) : EncodableEnum<EyeLabelCode> {
    UNKNOWN(0),
    RIGHT_IRIS(1),
    LEFT_IRIS(2);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): EyeLabelCode? {
            return EncodableEnum.Companion.fromCode<EyeLabelCode>(code, EyeLabelCode::class.java)
        }
    }
}