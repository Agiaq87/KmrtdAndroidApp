package it.giaquinto.kmrtd.kmrtdexampleapp.model

import kmrtd.support.DocumentNumber
import kmrtd.support.ICAODate

data class MRZInput(
    val documentNumber: DocumentNumber,
    val birthDate: ICAODate,
    val expireDate: ICAODate
)
