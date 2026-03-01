/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso19794.support

import kmrtd.lds.iso19794.FaceImageInfo

/** Hair color code based on Section 5.5.5 of ISO 19794-5.  */
enum class HairColor(
    /**
     * Creates a hair color.
     *
     * @param code the integer code for a color
     */
    val code: Int
) {
    UNSPECIFIED(FaceImageInfoConstants.HAIR_COLOR_UNSPECIFIED),
    BALD(FaceImageInfoConstants.HAIR_COLOR_BALD),
    BLACK(FaceImageInfoConstants.HAIR_COLOR_BLACK),
    BLONDE(FaceImageInfoConstants.HAIR_COLOR_BLONDE),
    BROWN(FaceImageInfoConstants.HAIR_COLOR_BROWN),
    GRAY(FaceImageInfoConstants.HAIR_COLOR_GRAY),
    WHITE(FaceImageInfoConstants.HAIR_COLOR_WHITE),
    RED(FaceImageInfoConstants.HAIR_COLOR_RED),
    GREEN(FaceImageInfoConstants.HAIR_COLOR_GREEN),
    BLUE(FaceImageInfoConstants.HAIR_COLOR_BLUE),
    UNKNOWN(FaceImageInfoConstants.HAIR_COLOR_UNKNOWN);

    companion object {
        /**
         * Returns a hair color value for the given code.
         *
         * @param i the integer code for a color
         *
         * @return the color value
         */
        @JvmStatic
        fun toHairColor(i: Int): HairColor {
            for (c in entries) {
                if (c.code == i) {
                    return c
                }
            }

            return HairColor.UNKNOWN
        }
    }
}