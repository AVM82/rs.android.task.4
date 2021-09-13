package org.rsschool.rsandroidtask4.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.rsschool.rsandroidtask4.data.Animal
import org.rsschool.rsandroidtask4.repository.Repository
import org.rsschool.rsandroidtask4.ui.AppState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _appState = MutableStateFlow(AppState())

    val animalsListFlow = repository.getAll().shareIn(viewModelScope, SharingStarted.Eagerly, 1)
    val appState: Flow<AppState> = _appState

    private fun updateAppState(modifier: AppState.() -> AppState) {
        _appState.value = _appState.value.modifier()
    }

    fun save(animal: Animal) {
        viewModelScope.launch {
            repository.save(animal)
        }
    }


}