package it.giaquinto.kmrtd.kmrtdexampleapp.ui.state

sealed class DataCheckerState(val message: String, val dataUiValidatorState: DataUiValidatorState) {
    class Empty(message: String) : DataCheckerState(message, DataUiValidatorState.Empty)
    class Valid(message: String) : DataCheckerState(message, DataUiValidatorState.Valid)
    class Invalid(message: String) : DataCheckerState(message, DataUiValidatorState.Invalid)
}
