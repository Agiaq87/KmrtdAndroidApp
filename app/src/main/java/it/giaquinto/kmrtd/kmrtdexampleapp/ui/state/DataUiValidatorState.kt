package it.giaquinto.kmrtd.kmrtdexampleapp.ui.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.theme.Success
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.theme.Warning

sealed class DataUiValidatorState(
    val contentDescription: String,
    val color: Color,
    val icon: ImageVector
){
    object Empty : DataUiValidatorState(
        "Empty value",
        Warning,
        Icons.Filled.Warning
    )
    object Valid : DataUiValidatorState(
        "Icon show valid value",
        Success,
        Icons.Filled.Check
    )
    object Invalid : DataUiValidatorState(
        "Invalid value",
        it.giaquinto.kmrtd.kmrtdexampleapp.ui.theme.Invalid,
        Icons.Filled.Error
    )
}
