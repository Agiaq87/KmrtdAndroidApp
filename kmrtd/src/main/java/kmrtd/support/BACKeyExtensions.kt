/*
 * Copyright (C) 2026 Alessandro Giaquinto
 * Kotlin port of JMRTD
 *
 * Licensed under LGPL 3.0
 */
package kmrtd.support

import kmrtd.BACKey
import java.util.Date
import java.util.Objects.toString

fun BACKey.from(documentNumber: DocumentNumber, birthDate: ICAODate, expireDate: ICAODate): BACKey =
    BACKey(
        documentNumber = documentNumber.value,
        dateOfBirth = birthDate.date,
        dateOfExpiry = expireDate.date
    )

fun BACKey.from(documentNumber: String, birthDate: Date, expireDate: Date): BACKey =
    BACKey(
        documentNumber,
        toString(birthDate),
        toString(expireDate)
    )