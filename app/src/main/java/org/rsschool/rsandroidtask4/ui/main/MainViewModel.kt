package org.rsschool.rsandroidtask4.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.rsschool.rsandroidtask4.ui.AppState

class MainViewModel : ViewModel() {
    private val _appState = MutableStateFlow(AppState())

    val appState: Flow<AppState> = _appState

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                updateAppState { copy(isEmptyAnimalsList = !isEmptyAnimalsList) }
            }
        }
    }


    private fun updateAppState (modifier: AppState.() -> AppState) {
        _appState.value = _appState.value.modifier()
    }




}