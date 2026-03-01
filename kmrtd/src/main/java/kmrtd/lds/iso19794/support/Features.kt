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
enum class Features {
    FEATURES_ARE_SPECIFIED,
    GLASSES,
    MOUSTACHE,
    BEARD,
    TEETH_VISIBLE,
    BLINK,
    MOUTH_OPEN,
    LEFT_EYE_PATCH,
    RIGHT_EYE_PATCH,
    DARK_GLASSES,
    DISTORTING_MEDICAL_CONDITION
}