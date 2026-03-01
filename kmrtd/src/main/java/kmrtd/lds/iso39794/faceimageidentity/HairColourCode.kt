package kmrtd.lds.iso39794.faceimageidentity

import kmrtd.lds.iso39794.EncodableEnum

enum class HairColourCode(override val code: Int) : EncodableEnum<HairColourCode> {
    UNKNOWN(0),
    OTHER(1),
    BALD(2),
    BLACK(3),
    BLONDE(4),
    BROWN(5),
    GREY(6),
    WHITE(7),
    RED(8),
    KNOWN_COLOURED(9);


    companion object {
        @JvmStatic
        fun fromCode(code: Int): HairColourCode? {
            return EncodableEnum.Companion.fromCode<HairColourCode>(
                code,
                HairColourCode::class.java
            )
        }
    }
}