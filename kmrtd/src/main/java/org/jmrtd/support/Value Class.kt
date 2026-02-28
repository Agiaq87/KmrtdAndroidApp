/*
 * Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package org.jmrtd.support

@JvmInline
value class DocumentNumber(val value: String) {
    init {
        require(value.length == 9) { "Document number must be 9 characters long" }
        require(value.matches("""^[a-zA-Z0-9]{6,20}$""".toRegex())) { "Document number must be alphanumeric" }
    }
}

@JvmInline
value class ICAODate(val date: String) {
    init {
        require(date.matches("""^\d{6}$""".toRegex())) { "Date must be a string of digits in format yyMMdd" }
        require(date.length == 6) { "Date must be 6 characters long in format yyMMdd"}
    }
}