package org.rsschool.rsandroidtask4.ui

data class AppState(
    val isEmptyAnimalsList: Boolean = true,
    val order: String = "name",
    val useCursor: Boolean = false
)
