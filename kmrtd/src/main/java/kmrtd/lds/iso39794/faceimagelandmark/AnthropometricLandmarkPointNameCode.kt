package kmrtd.lds.iso39794.faceimagelandmark

import kmrtd.lds.iso39794.EncodableEnum
import kmrtd.lds.iso39794.FaceImageLandmarkKind

enum class AnthropometricLandmarkPointNameCode(override val code: Int) :
    EncodableEnum<AnthropometricLandmarkPointNameCode>, FaceImageLandmarkKind {
    POINTCODE_01_01(0),
    POINTCODE_01_02(1),
    POINTCODE_01_05(2),
    POINTCODE_01_06(3),
    POINTCODE_01_07(4),
    POINTCODE_01_08(5),
    POINTCODE_01_09(6),
    POINTCODE_02_01(7),
    POINTCODE_02_02(8),
    POINTCODE_02_03(9),
    POINTCODE_02_04(10),
    POINTCODE_02_05(11),
    POINTCODE_02_06(12),
    POINTCODE_02_07(13),
    POINTCODE_02_09(14),
    POINTCODE_02_10(15),
    POINTCODE_03_01(16),
    POINTCODE_03_02(17),
    POINTCODE_03_03(18),
    POINTCODE_03_04(19),
    POINTCODE_03_05(20),
    POINTCODE_03_06(21),
    POINTCODE_03_07(22),
    POINTCODE_03_08(23),
    POINTCODE_03_09(24),
    POINTCODE_03_10(25),
    POINTCODE_03_11(26),
    POINTCODE_03_12(27),
    POINTCODE_04_01(28),
    POINTCODE_04_02(29),
    POINTCODE_04_03(30),
    POINTCODE_04_04(31),
    POINTCODE_05_01(32),
    POINTCODE_05_02(33),
    POINTCODE_05_03(34),
    POINTCODE_05_04(35),
    POINTCODE_05_06(36);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): AnthropometricLandmarkPointNameCode? {
            return EncodableEnum.Companion.fromCode<AnthropometricLandmarkPointNameCode>(
                code,
                AnthropometricLandmarkPointNameCode::class.java
            )
        }
    }
}