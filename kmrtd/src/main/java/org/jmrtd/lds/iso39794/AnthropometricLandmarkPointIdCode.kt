/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package org.jmrtd.lds.iso39794

enum class AnthropometricLandmarkPointIdCode(override val code: Int) :
    EncodableEnum<AnthropometricLandmarkPointIdCode>, FaceImageLandmarkKind {
    V(0),
    G(1),
    OP(2),
    EU_LEFT(3),
    EU_RIGHT(4),
    FT_LEFT(5),
    FT_RIGHT(6),
    TR(7),
    ZY_LEFT(8),
    ZY_RIGHT(9),
    GO_LEFT(10),
    GO_RIGHT(11),
    SL(12),
    PG(13),
    GN(14),
    CDL_LEFT(15),
    CDL_RIGHT(16),
    EN_LEFT(17),
    EN_RIGHT(18),
    EX_LEFT(19),
    EX_RIGHT(20),
    P_LEFT(21),
    P_RIGHT(22),
    OR_LEFT(23),
    OR_RIGHT(24),
    PS_LEFT(25),
    PS_RIGHT(26),
    PI_LEFT(27),
    PI_RIGHT(28),
    OS_LEFT(29),
    OS_RIGHT(30),
    SCI_LEFT(31),
    SCI_RIGHT(32),
    N(33),
    SE(34),
    AL_LEFT(35),
    AL_RIGHT(36),
    PRN(37),
    SN(38),
    SBAL(39),
    AC_LEFT(40),
    AC_RIGHT(41),
    MF_LEFT(42),
    MF_RIGHT(43),
    CPH_LEFT(44),
    CPH_RIGHT(45),
    LS(46),
    LI(47),
    CH_LEFT(48),
    CH_RIGHT(49),
    STO(50),
    SA_LEFT(51),
    SA_RIGHT(52),
    SBA_LEFT(53),
    SBA_RIGHT(54),
    PRA_LEFT(55),
    PRA_RIGHT(56),
    PA(57),
    OBS_LEFT(58),
    OBS_RIGHT(59),
    OBI(60),
    PO(61),
    T(62);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): AnthropometricLandmarkPointIdCode? {
            return EncodableEnum.fromCode<AnthropometricLandmarkPointIdCode>(
                code,
                AnthropometricLandmarkPointIdCode::class.java
            )
        }
    }
}