package it.giaquinto.kmrtd.kmrtdexampleapp.ui.state

data class HomeScreenState(
    val documentNumber: String = "CA22913WY",
    val birthDate: String = "870106",
    val expireDate: String = "350106",
    val documentNumberCheckerState: DataCheckerState = DataCheckerState.Empty("CA22913WY"),
    val dateBirthCheckerState: DataCheckerState = DataCheckerState.Empty("870106"),
    val dateExpireCheckerState: DataCheckerState = DataCheckerState.Empty("350106")
)
