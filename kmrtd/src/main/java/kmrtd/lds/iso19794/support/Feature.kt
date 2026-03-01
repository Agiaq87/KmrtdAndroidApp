/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso19794.support

/**
 * Feature flags meaning based on Section 5.5.6 of ISO 19794-5.
 *
 * [see](https://www.iso.org/obp/ui/es/#iso:std:iso-iec:19794:-5:ed-2:v1:en)
 * [see](https://egovstandards.gov.in/sites/default/files/Face_Image_Data_Standard_Ver1.0.pdf)
 * @author The JMRTD team (info@jmrtd.org)
 * @author Alessandro Giaquinto (agiaquinto87@gmail.com)
 * @version 2.0
 */
enum class Feature(val mask: Int) {
    UNSPECIFIED(0x000000),
    SPECIFIED(0x000001),
    GLASSES(0x000002),
    MOUSTACHE(0x000004),
    BEARD(0x000008),
    TEETH_VISIBLE(0x000010),
    BLINK(0x000020),
    MOUTH_OPEN(0x000040),
    LEFT_EYE_PATCH(0x000080),
    RIGHT_EYE_PATCH(0x000100),
    DARK_GLASSES(0x000200),
    DISTORTING_MEDICAL_CONDITION(0x000400);

    companion object {
        /** Decodes a bitmask into a Set of features. */
        @JvmStatic
        fun fromMask(featureMask: Int): Set<Feature> =
            entries.filter { featureMask and it.mask != 0 }.toSet()

        /** Encodes a Set of features into a bitmask. */
        @JvmStatic
        fun toMask(features: Set<Feature>): Int =
            features.fold(0) { acc, f -> acc or f.mask }
    }
}