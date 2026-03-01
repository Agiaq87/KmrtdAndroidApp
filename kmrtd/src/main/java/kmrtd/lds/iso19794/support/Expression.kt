/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso19794.support

/** Expression code based on Section 5.5.7 of ISO 19794-5.  */
enum class Expression(val code: Short) {
    UNSPECIFIED(0x0000),
    NEUTRAL(0x0001),
    SMILE_CLOSED(0x0002),
    SMILE_OPEN(0x0003),
    RAISED_EYEBROWS(0x0004),
    EYES_LOOKING_AWAY(0x0005),
    SQUINTING(0x0006),
    FROWNING(0x0007);

    companion object {
        @JvmStatic
        fun toExpression(code: Short): Expression? =
            when (code) {
                FaceImageInfoConstants.EXPRESSION_UNSPECIFIED -> UNSPECIFIED
                FaceImageInfoConstants.EXPRESSION_NEUTRAL -> NEUTRAL
                FaceImageInfoConstants.EXPRESSION_SMILE_CLOSED -> SMILE_CLOSED
                FaceImageInfoConstants.EXPRESSION_SMILE_OPEN -> SMILE_OPEN
                FaceImageInfoConstants.EXPRESSION_RAISED_EYEBROWS -> RAISED_EYEBROWS
                FaceImageInfoConstants.EXPRESSION_EYES_LOOKING_AWAY -> EYES_LOOKING_AWAY
                FaceImageInfoConstants.EXPRESSION_SQUINTING -> SQUINTING
                FaceImageInfoConstants.EXPRESSION_FROWNING -> FROWNING
                else -> null
            }
    }
}