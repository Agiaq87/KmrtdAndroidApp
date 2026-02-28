package it.giaquinto.kmrtd.kmrtdexampleapp.ui.widget

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.state.DataCheckerState

@Composable
fun DocumentNumberCheckerWidget(
    scaffoldPadding: PaddingValues,
    onValueChanged: (String) -> Unit,
    dataCheckerState: DataCheckerState
) = Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(scaffoldPadding)
            .border(
                width = 1.dp,
                color = Color.Gray,
                shape = RoundedCornerShape(8.dp)
            )
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            value = dataCheckerState.message,
            onValueChange = { onValueChanged(it) },
            label = { Text(dataCheckerState.message) },
            singleLine = true,
            modifier = Modifier.weight(0.75f).padding(
                start = 8.dp,
                top = 0.dp,
                end = 0.dp,
                bottom = 8.dp
            )
        )

        DataUiValidator(dataCheckerState.dataUiValidatorState, PaddingValues(8.dp))
    }