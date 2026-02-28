/*
 * JMRTD - A Java API for accessing machine readable travel documents.
 *
 * Copyright (C) 2006 - 2018  The JMRTD team
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * $Id: CVCPrincipal.java 1808 2019-03-07 21:32:19Z martijno $
 */
/*
 * Modified work Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.cert

import net.sf.scuba.data.Country
import java.io.Serializable
import java.security.Principal
import java.util.Locale
import java.util.logging.Logger

/**
 * Card verifiable certificate principal.
 * This just wraps the EJBCA implementation.
 * 
 * @author The JMRTD team (info@jmrtd.org)
 * 
 * @version $Revision: 1808 $
 */
data class CVCPrincipal(
    val country: Country,
    val mnemonic: String,
    val seqNumber: String
) : Principal, Serializable {
    /*    private var country: Country? = null

        */
    /**
     * Returns the mnemonic.
     * 
     * @return the mnemonic
     *//*
    @JvmField
    val mnemonic: String

    */
    /**
     * Returns the sequence number.
     *
     * @return the seqNumber
     *//*
    @JvmField
    val seqNumber: String*/

    /**
     * Constructs a principal.
     * 
     * @param name a name with format Country (2F) | Mnemonic (9V) | SeqNum (5F).
     */
    /*constructor(name: String) {
        requireNotNull(name) { "Name should be <Country (2F)><Mnemonic (9V)><SeqNum (5F)> formatted, found null" }
        require(!(name.length < 2 + 5 || name.length > 2 + 9 + 5)) { "Name should be <Country (2F)><Mnemonic (9V)><SeqNum (5F)> formatted, found \"$name\"" }

        val alpha2Code = name.substring(0, 2).uppercase(Locale.getDefault())
        try {
            country = Country.getInstance(alpha2Code)
        } catch (iae: IllegalArgumentException) {
            LOGGER.log(Level.FINE, "Could not find country for " + alpha2Code, iae)
            country = UnknownCountry(name) *//*Country() {

        private static final long serialVersionUID = 345841304964161797L;

        @Override
        public int valueOf() {
          return -1;
        }

        @Override
        public String getName() {
          return "Unknown";
        }

        @Override
        public String getNationality() {
          return "Unknown";
        }

        @Override
        public String toAlpha2Code() {
          return alpha2Code;
        }

        @Override
        public String toAlpha3Code() {
          return "XXX";
        }

      };*//*
        }
        mnemonic = name.substring(2, name.length - 5)
        seqNumber = name.substring(name.length - 5)
    }*/
    /*
    /**
     * Constructs a principal.
     * 
     * @param country the country
     * @param mnemonic the mnemonic
     * @param seqNumber the sequence number
     */
    constructor(country: Country, mnemonic: String, seqNumber: String) {
        require(!(mnemonic == null || mnemonic.length > 9)) { "Wrong length mnemonic" }
        require(!(seqNumber == null || seqNumber.length != 5)) { "Wrong length seqNumber" }
        this.country = country
        this.mnemonic = mnemonic
        this.seqNumber = seqNumber
    }*/

    /**
     * Consists of the concatenation of
     * country code (length 2), mnemonic (length &lt; 9) and
     * sequence number (length 5).
     * 
     * @return the name of the principal
     */
    override fun getName(): String {
        return country.toAlpha2Code() + mnemonic + seqNumber
    }

    /**
     * Returns a textual representation of this principal.
     * 
     * @return a textual representation of this principal
     */
    override fun toString(): String {
        return country.toAlpha2Code() + "/" + mnemonic + "/" + seqNumber
    }
    /*
        */
    /**
     * Returns the country.
     * 
     * @return the country
     *//*
    fun getCountry(): Country {
        return country
    }*/

    /**
     * Tests for equality with respect to another object.
     * 
     * @param otherObj another object
     * 
     * @return whether this principal equals the other object
     */
    /*override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other === this) {
            return true
        }
        if (other.javaClass != this.javaClass) {
            return false
        }

        val otherPrincipal = other as CVCPrincipal
        return otherPrincipal.country == this.country
                && otherPrincipal.mnemonic == this.mnemonic
                && otherPrincipal.seqNumber == this.seqNumber
    }*/

    /**
     * Returns a hash code of this object.
     * 
     * @return the hash code
     */
    /*override fun hashCode(): Int {
        return 2 * getName().hashCode() + 1231211
    }*/

    companion object {
        private const val serialVersionUID = -4905647207367309688L

        private val LOGGER: Logger = Logger.getLogger("kmrtd")

        /**
         * Factory method to create a principal from a name.
         *
         */
        @JvmStatic
        fun fromName(name: String): CVCPrincipal {
            require(!(name.length < 2 + 5 || name.length > 2 + 9 + 5)) { "Name should be <Country (2F)><Mnemonic (9V)><SeqNum (5F)> formatted, found \"$name\"" }

            val alpha2Code = name.substring(0, 2).uppercase(Locale.getDefault())
            val country =
                runCatching { Country.getInstance(alpha2Code) }.getOrElse { UnknownCountry(name) }

            return CVCPrincipal(
                country = country,
                mnemonic = name.substring(2, name.length - 5),
                seqNumber = name.substring(name.length - 5)
            )
        }
    }
}
