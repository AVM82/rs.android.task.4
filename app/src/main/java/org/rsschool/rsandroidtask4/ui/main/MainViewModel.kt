package org.rsschool.rsandroidtask4.ui.main

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.rsschool.rsandroidtask4.R
import org.rsschool.rsandroidtask4.data.Animal
import org.rsschool.rsandroidtask4.repository.Repository
import org.rsschool.rsandroidtask4.ui.AppState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val pref: SharedPreferences,
    application: Application
) : ViewModel() {

    private val keyPrefOrder = application.getString(R.string.KEY_PREF_ORDER)

    private val _appState = MutableStateFlow(
        AppState(
            order = pref.getString(
                keyPrefOrder,
                "name"
            ).toString()
        )
    )
    val appState: Flow<AppState> = _appState
    var animalsListFlow = fetchAnimalsList()
//            .shareIn(viewModelScope, SharingStarted.Eagerly, 1)

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            when (key) {
                keyPrefOrder -> {
                    updateAppState {
                        copy(order = prefs.getString(keyPrefOrder, "name").toString())
                    }
                    animalsListFlow = fetchAnimalsList()
                }
            }
        }

    init {
        pref.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    private fun fetchAnimalsList() = repository.getAll(_appState.value.order)

    override fun onCleared() {
        super.onCleared()
        pref.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    fun toggleEmptyListImage(isEmptyList: Boolean) {
        updateAppState { copy(isEmptyAnimalsList = isEmptyList) }
    }

    private fun updateAppState(modifier: AppState.() -> AppState) {
        _appState.value = _appState.value.modifier()
    }

    fun save(animal: Animal) {
        viewModelScope.launch {
            repository.save(animal)
        }
    }

    fun delete(animal: Animal) {
        viewModelScope.launch {
            repository.delete(animal)
        }
    }
}