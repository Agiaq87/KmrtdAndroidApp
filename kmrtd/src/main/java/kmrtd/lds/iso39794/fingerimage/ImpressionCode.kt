/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.lds.iso39794.fingerimage

import kmrtd.lds.iso39794.EncodableEnum

enum class ImpressionCode(override val code: Int) : EncodableEnum<ImpressionCode> {
    PLAIN_CONTACT(0),
    ROLLED_CONTACT(1),
    LATENT_IMAGE(4),
    SWIPE_CONTACT(8),
    STATIONARY_SUBJECT_CONTACTLESS_PLAIN(24),
    STATIONARY_SUBJECT_CONTACTLESS_ROLLED(25),
    MOVING_SUBJECT_CONTACTLESS_PLAIN(41),
    MOVING_SUBJECT_CONTACTLESS_ROLLED(42),
    OTHER_IMPRESSION(28),
    UNKNOWN_IMPRESSION(29);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): ImpressionCode? {
            return EncodableEnum.fromCode<ImpressionCode>(code, ImpressionCode::class.java)
        }
    }
}