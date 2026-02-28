/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794

enum class AnthropometricLandmarkNameCode(override val code: Int) :
    EncodableEnum<AnthropometricLandmarkNameCode>, FaceImageLandmarkKind {
    VERTEX(0),
    GLABELLA(1),
    OPISTHOCRANION(2),
    EURION_LEFT(3),
    EURION_RIGHT(4),
    FRONTOTEMPORALE_LEFT(5),
    FRONTOTEMPORALE_RIGHT(6),
    TRICHION(7),
    ZYGION_LEFT(8),
    ZYGION_RIGHT(9),
    GONION_LEFT(10),
    GONION_RIGHT(11),
    SUBLABIALE(12),
    POGONION(13),
    MENTON(14),
    CONDYLION_LATERALE_LEFT(15),
    CONDYLION_LATERALE_RIGHT(16),
    ENDOCANTHION_LEFT(17),
    ENDOCANTHION_RIGHT(18),
    EXOCANTHION_LEFT(19),
    EXOCANTHION_RIGHT(20),
    CENTER_POINT_OF_PUPIL_LEFT(21),
    CENTER_POINT_OF_PUPIL_RIGHT(22),
    ORBITALE_LEFT(23),
    ORBITALE_RIGHT(24),
    PALPEBRALE_SUPERIUS_LEFT(25),
    PALPEBRALE_SUPERIUS_RIGHT(26),
    PALPEBRALE_INFERIUS_LEFT(27),
    PALPEBRALE_INFERIUS_RIGHT(28),
    ORBITALE_SUPERIUS_LEFT(29),
    ORBITALE_SUPERIUS_RIGHT(30),
    SUPERCILIARE_LEFT(31),
    SUPERCILIARE_RIGHT(32),
    NASION(33),
    SELLION(34),
    ALARE_LEFT(35),
    ALARE_RIGHT(36),
    PRONASALE(37),
    SUBNASALE(38),
    SUBALARE(39),
    ALAR_CURVATURE_LEFT(40),
    ALAR_CURVATURE_RIGHT(41),
    MAXILLOFRONTALE(42),
    CHRISTA_PHILTRA_LANDMARK_LEFT(43),
    CHRISTA_PHILTRA_LANDMARK_RIGHT(44),
    LABIALE_SUPERIUS(45),
    LABIALE_INFERIUS(46),
    CHEILION_LEFT(47),
    CHEILION_RIGHT(48),
    STOMION(49),
    SUPERAURALE_LEFT(50),
    SUPERAURALE_RIGHT(51),
    SUBAURALE_LEFT(52),
    SUBAURALE_RIGHT(53),
    PREAURALE(54),
    POSTAURALE(55),
    OTOBASION_SUPERIUS_LEFT(56),
    OTOBASION_SUPERIUS_RIGHT(57),
    OTOBASION_INFERIUS(58),
    PORION(59),
    TRAGION(60);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): AnthropometricLandmarkNameCode? {
            return EncodableEnum.fromCode<AnthropometricLandmarkNameCode>(
                code,
                AnthropometricLandmarkNameCode::class.java
            )
        }
    }
}