package it.giaquinto.kmrtd.kmrtdexampleapp.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.state.DataUiValidatorState
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.theme.Invalid
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.theme.Success
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.theme.Warning

@Composable
fun DataUiValidator(
    state: DataUiValidatorState,
    padding: PaddingValues
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(padding)
    ) {
        Icon(
            imageVector = state.icon,
            contentDescription = state.contentDescription,
            tint = state.color,
            modifier = Modifier.width(40.dp).height(40.dp)
        )
    }
}

@Preview
@Composable
fun DataUiValidatorPreview() {
    Scaffold(modifier = Modifier.fillMaxSize().padding(32.dp)) { padding ->
        Column(
            modifier = Modifier.padding(padding).padding(16.dp)
        ) {
            DataUiValidator(DataUiValidatorState.Empty, padding)
            DataUiValidator(DataUiValidatorState.Valid, padding)
            DataUiValidator(DataUiValidatorState.Invalid, padding)
        }
    }
}