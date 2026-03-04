package it.giaquinto.kmrtd.kmrtdexampleapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.state.DataCheckerState
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.state.HomeScreenState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeViewModel : ViewModel() {
    var uiState by mutableStateOf(HomeScreenState())
        private set

    private val documentNumberRegex = """^[A-Z0-9]{6,20}$""".toRegex()
    private val icaoRegex = """^\d{6}$""".toRegex()


    fun onDocumentNumberChange(newValue: String) {
        val upperValue = newValue.uppercase()

        val newState = when {
            upperValue.isEmpty() -> DataCheckerState.Empty("Inserisci numero")
            upperValue.matches(documentNumberRegex) -> DataCheckerState.Valid(upperValue)
            else -> DataCheckerState.Invalid("Formato non valido")
        }

        val isValid = validateAll()

        uiState = uiState.copy(
            documentNumber = upperValue,
            documentNumberCheckerState = newState
        )
    }

    fun onBirthDateSelected(millis: Long?) {
        millis?.let {
            val birthDate = convertMillisToDate(it)

            val newState = when {
                birthDate.isEmpty() -> DataCheckerState.Empty("Inserisci data")
                birthDate.matches(icaoRegex) -> DataCheckerState.Valid(birthDate)
                else -> DataCheckerState.Invalid("Formato non valido")
            }

            val isValid = validateAll()

            uiState = uiState.copy(
                birthDate = birthDate,
                dateBirthCheckerState = newState
            )
        }
    }

    fun onExpireDateSelected(millis: Long?) {
        millis?.let {
            val expireDate = convertMillisToDate(it)

            val newState = when {
                expireDate.isEmpty() -> DataCheckerState.Empty("Inserisci data")
                expireDate.matches(icaoRegex) -> DataCheckerState.Valid(expireDate)
                else -> DataCheckerState.Invalid("Formato non valido")
            }

            val isValid = validateAll()

            uiState = uiState.copy(
                expireDate = expireDate,
                dateExpireCheckerState = newState
            )
        }
    }

    private fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("yyMMdd", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    private fun validateAll(): Boolean =
        uiState.documentNumber.matches(documentNumberRegex) &&
                uiState.birthDate.matches(icaoRegex) &&
                uiState.expireDate.matches(icaoRegex)
}