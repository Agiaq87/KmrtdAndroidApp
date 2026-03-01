package kmrtd.lds.iso39794.irisimage

import kmrtd.lds.iso39794.EncodableEnum

enum class IrisImageKindCode(override val code: Int) : EncodableEnum<IrisImageKindCode> {
    UNCROPPED(1),
    VGA(2),
    CROPPED(3),
    CROPPED_AND_MASKED(7);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): IrisImageKindCode? {
            return EncodableEnum.Companion.fromCode<IrisImageKindCode>(
                code,
                IrisImageKindCode::class.java
            )
        }
    }
}