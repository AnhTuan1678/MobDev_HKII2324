package com.example.mycity.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mycity.data.CityCategory
import com.example.mycity.data.Place
import com.example.mycity.data.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState:StateFlow<UiState> = _uiState.asStateFlow()

    fun changeCategory(category: CityCategory) {
        _uiState.update {
            it.copy(
                category = category,
            )
        }
        Log.d("CityViewModel", "ui state: ${_uiState.value.category?.places?.size}")
    }

    fun changePlace(place: Place) {
        _uiState.update {
            it.copy(place = place)
        }
    }
}