package com.example.btl.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class DualRowsState(
    val first: Int?,
    val second: Int?,
    val total: Int?,
    val isCorrect: Boolean
)

class DualRowsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<DualRowsState>(
        DualRowsState(
            first = null,
            second = null,
            total = null,
            isCorrect = false
        )
    )
    val uiState: StateFlow<DualRowsState> get() = _uiState

    init{
        resetDualRowsState()
    }

    fun updateDualRowsState(first: Int?, second: Int?, total: Int?, isCorrect: Boolean) {
        _uiState.value = DualRowsState(
            first = first,
            second = second,
            total = total,
            isCorrect = isCorrect
        )
    }

    private fun resetDualRowsState(total: Int? = 20) {
        _uiState.value = DualRowsState(
            first = null,
            second = null,
            total = total,
            isCorrect = false
        )
    }

    fun checkAnswer() {
        val currentDualRowsState = _uiState.value
        if (currentDualRowsState.first != null && currentDualRowsState.second != null && currentDualRowsState.total != null) {
            val isCorrect = currentDualRowsState.first + currentDualRowsState.second == currentDualRowsState.total
            _uiState.value = currentDualRowsState.copy(isCorrect = isCorrect)
        }
    }

    fun changeTotal(total: Int) {
        val currentDualRowsState = _uiState.value
        _uiState.value = currentDualRowsState.copy(total = total)
        changeTotal(total)
    }
}
