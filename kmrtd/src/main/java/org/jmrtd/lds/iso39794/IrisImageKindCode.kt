/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package org.jmrtd.lds.iso39794

enum class IrisImageKindCode(override val code: Int) : EncodableEnum<IrisImageKindCode> {
    UNCROPPED(1),
    VGA(2),
    CROPPED(3),
    CROPPED_AND_MASKED(7);

    companion object {
        @JvmStatic
        fun fromCode(code: Int): IrisImageKindCode? {
            return EncodableEnum.fromCode<IrisImageKindCode>(code, IrisImageKindCode::class.java)
        }
    }
}