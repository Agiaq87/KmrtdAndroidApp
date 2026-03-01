package kmrtd.lds.iso39794.faceimage2d

import kmrtd.lds.iso39794.EncodableEnum

enum class FaceImageKind2DCode(override val code: Int) : EncodableEnum<FaceImageKind2DCode> {
    MRTD(0),
    GENERAL_PURPOSE(1);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): FaceImageKind2DCode? {
            return EncodableEnum.Companion.fromCode<FaceImageKind2DCode>(
                code,
                FaceImageKind2DCode::class.java
            )
        }
    }
}