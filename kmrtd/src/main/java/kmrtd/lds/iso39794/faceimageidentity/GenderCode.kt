package kmrtd.lds.iso39794.faceimageidentity

import kmrtd.lds.iso39794.EncodableEnum

enum class GenderCode(override val code: Int) : EncodableEnum<GenderCode> {
    UNKNOWN(0),
    OTHER(1),
    MALE(2),
    FEMALE(3);


    companion object {
        @JvmStatic
        fun fromCode(code: Int): GenderCode? {
            return EncodableEnum.Companion.fromCode<GenderCode>(
                code,
                GenderCode::class.java
            )
        }
    }
}