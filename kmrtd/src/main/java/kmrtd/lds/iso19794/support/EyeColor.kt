/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso19794.support

/** Eye color code based on Section 5.5.4 of ISO 19794-5.  */
enum class EyeColor(
    /**
     * Creates an eye color.
     *
     * @param code the ISO19794-5 integer code for the color
     */
    val code: Int
) {
    UNSPECIFIED(FaceImageInfoConstants.EYE_COLOR_UNSPECIFIED),
    BLACK(FaceImageInfoConstants.EYE_COLOR_BLACK),
    BLUE(FaceImageInfoConstants.EYE_COLOR_BLUE),
    BROWN(FaceImageInfoConstants.EYE_COLOR_BROWN),
    GRAY(FaceImageInfoConstants.EYE_COLOR_GRAY),
    GREEN(FaceImageInfoConstants.EYE_COLOR_GREEN),
    MULTI_COLORED(FaceImageInfoConstants.EYE_COLOR_MULTI_COLORED),
    PINK(FaceImageInfoConstants.EYE_COLOR_PINK),
    UNKNOWN(FaceImageInfoConstants.EYE_COLOR_UNKNOWN);


    companion object {
        /**
         * Returns an eye color value for the given code.
         *
         * @param i the integer code for a color
         *
         * @return the color value
         */
        @JvmStatic
        fun toEyeColor(i: Int): EyeColor {
            for (c in entries) {
                if (c.code == i) {
                    return c
                }
            }
            return UNKNOWN
        }
    }
}