/*
 * Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.cert

import net.sf.scuba.data.Country
import java.util.Locale

class UnknownCountry(name: String) : Country() {

    private val alpha2Code: String = name.substring(0, 2).uppercase(Locale.getDefault())

    override fun valueOf(): Int {
        TODO("Not yet implemented")
    }

    override fun getName(): String = "Unknown"

    override fun getNationality(): String = "Unknown"

    override fun toAlpha2Code(): String = alpha2Code

    override fun toAlpha3Code(): String = "XXX"

}
