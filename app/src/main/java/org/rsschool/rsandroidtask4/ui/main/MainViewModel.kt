package org.rsschool.rsandroidtask4.ui.main

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.rsschool.rsandroidtask4.R
import org.rsschool.rsandroidtask4.data.Animal
import org.rsschool.rsandroidtask4.repository.AnimalsDataBase
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
    private val keyPrefProvider = application.getString(R.string.KEY_PREF_PROVIDER)

    private val _appState = MutableStateFlow(
        AppState(
            order = pref.getString(
                keyPrefOrder,
                AnimalsDataBase.COLUMN_TABLE_ANIMALS_NAME
            ).toString(),
            useCursor = pref.getBoolean(keyPrefProvider, false)
        )
    )
    val appState: Flow<AppState> = _appState
    private val _animalsListFlow =  MutableStateFlow<List<Animal>>(emptyList())
    var animalsListFlow:StateFlow<List<Animal>> = _animalsListFlow

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            when (key) {
                keyPrefOrder -> {
                    updateAppState {
                        copy(
                            order = prefs.getString(
                                keyPrefOrder,
                                AnimalsDataBase.COLUMN_TABLE_ANIMALS_NAME
                            ).toString()
                        )
                    }
                }
                keyPrefProvider -> {
                    updateAppState {
                        copy(useCursor = prefs.getBoolean(keyPrefProvider, false))
                    }
                }
            }
        }

    init {
        pref.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
        appState.onEach {updateAnimalsList() }.launchIn(viewModelScope)
    }

    private fun updateAnimalsList() {
        Log.d("updateAnimalsList", "Start")
        Log.d("updateAnimalsList", _animalsListFlow.value.toString())
        repository.getAll(_appState.value.order).onEach { _animalsListFlow.value = it }.launchIn(viewModelScope)
        Log.d("updateAnimalsList", "END")
        Log.d("updateAnimalsList", _animalsListFlow.value.toString())
    }

//    private fun fetch() {
//        animalsListFlow = repository.getAll(_appState.value.order)
//    }


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
        viewModelScope.launch(Dispatchers.IO) {
            repository.save(animal)
            updateAnimalsList()

        }
    }

    fun delete(animal: Animal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(animal)
            updateAnimalsList()
        }
    }
}
