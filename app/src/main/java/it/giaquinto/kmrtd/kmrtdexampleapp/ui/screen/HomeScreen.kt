package it.giaquinto.kmrtd.kmrtdexampleapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import it.giaquinto.kmrtd.kmrtdexampleapp.R
import org.kmrtd.support.DocumentNumber
import org.kmrtd.support.ICAODate
import it.giaquinto.kmrtd.kmrtdexampleapp.model.MRZInput
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.viewmodel.HomeViewModel
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.widget.DateCheckerWidget
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.widget.DocumentNumberCheckerWidget

@Composable
fun HomeScreen(
    padding: PaddingValues,
    viewModel: HomeViewModel = viewModel(),
    showGithub : () -> Unit,
    showLinkedin : () -> Unit,
    jmrtd : (MRZInput) -> Unit,
    kmrtd : (MRZInput) -> Unit,
) {
    val state = viewModel.uiState

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            text = stringResource(R.string.home_screen_title),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        DocumentNumberCheckerWidget(
            scaffoldPadding = padding,
            onValueChanged = { viewModel.onDocumentNumberChange(it) },
            dataCheckerState = state.documentNumberCheckerState
        )

        Spacer(modifier = Modifier.height(12.dp))

        DateCheckerWidget(
            scaffoldPadding = padding,
            onDateChanged = {
                viewModel.onBirthDateSelected(it)
            },
            dataCheckerState = state.dateBirthCheckerState
        )

        Spacer(modifier = Modifier.height(12.dp))

        DateCheckerWidget(
            scaffoldPadding = padding,
            onDateChanged = {
                viewModel.onExpireDateSelected(it)
            },
            dataCheckerState = state.dateExpireCheckerState
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (state.jmrtdButtonEnabled) {
            Button(
                onClick = {
                    //baseViewModel.acquireMrzData(MrzData(documentNumber, birthDate, expirationDate))
                    jmrtd(
                        MRZInput(
                            DocumentNumber(viewModel.uiState.documentNumber),
                            ICAODate(viewModel.uiState.birthDate),
                            ICAODate(viewModel.uiState.expireDate)
                        )
                    )
                },
                //enabled = isValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.home_screen_read_button))
            }
        }


    }
}