package it.giaquinto.kmrtd.kmrtdexampleapp.model

import org.jmrtd.support.DocumentNumber
import org.jmrtd.support.ICAODate

data class MRZInput(
    val documentNumber: DocumentNumber,
    val birthDate: ICAODate,
    val expireDate: ICAODate
)
