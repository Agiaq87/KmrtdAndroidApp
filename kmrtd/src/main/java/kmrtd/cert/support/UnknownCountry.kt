package kmrtd.cert.support

import net.sf.scuba.data.Country

data class UnknownCountry(val alpha2Code: String) : Country() {
    override fun valueOf(): Int = -1

    override fun getName(): String = "Unknown"

    override fun getNationality(): String = "Unknown"

    override fun toAlpha2Code(): String = alpha2Code

    override fun toAlpha3Code(): String = "XXX"
}