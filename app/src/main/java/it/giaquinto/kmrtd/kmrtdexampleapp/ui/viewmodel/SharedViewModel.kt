package it.giaquinto.kmrtd.kmrtdexampleapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import it.giaquinto.kmrtd.kmrtdexampleapp.framework.KmrtdResultBuilder

class SharedViewModel : ViewModel() {

    var resultsState by mutableStateOf(false)

    var kmrtdResultBuilder: KmrtdResultBuilder? = null
    var jmrtdResultBuilder: KmrtdResultBuilder? = null

    fun updateJmrtd(jmrtdResultBuilder: KmrtdResultBuilder) {
        this@SharedViewModel.jmrtdResultBuilder = jmrtdResultBuilder
        resultsState = kmrtdResultBuilder != null && jmrtdResultBuilder != null
    }

    fun updateKmrtd(kmrtdResultBuilder: KmrtdResultBuilder) {
        this@SharedViewModel.kmrtdResultBuilder = kmrtdResultBuilder
        resultsState = kmrtdResultBuilder != null && jmrtdResultBuilder != null
    }
}