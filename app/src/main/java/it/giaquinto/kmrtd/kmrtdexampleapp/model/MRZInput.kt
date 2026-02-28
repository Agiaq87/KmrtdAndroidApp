package it.giaquinto.kmrtd.kmrtdexampleapp.model

import org.kmrtd.support.DocumentNumber
import org.kmrtd.support.ICAODate

data class MRZInput(
    val documentNumber: DocumentNumber,
    val birthDate: ICAODate,
    val expireDate: ICAODate
)
