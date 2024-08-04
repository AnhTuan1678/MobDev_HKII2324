package com.example.btl.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class DualPlayerState(
    val first: Int?,
    val second: Int?,
    val total: Int?,
    val isCorrect: Boolean?,
    val correctCount: Int,
    val isFinished: Boolean = false
)

class DualPlayerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<DualPlayerState>(
        DualPlayerState(
            first = null,
            second = null,
            total = null,
            isCorrect = null,
            correctCount = 0
        )
    )
    val uiState: StateFlow<DualPlayerState> get() = _uiState

    init {
        reset((1..100).random())
    }

    fun updateDualPlayerState(first: Int?, second: Int?, total: Int?, isCorrect: Boolean?) {
        _uiState.value = DualPlayerState(
            first = first,
            second = second,
            total = total,
            isCorrect = isCorrect,
            correctCount = _uiState.value.correctCount + if (isCorrect == true) 1 else 0
        )
    }

    fun reset(total: Int? = 20) {
        _uiState.value = DualPlayerState(
            first = null,
            second = null,
            total = total,
            isCorrect = null,
            correctCount = 0
        )
    }

    fun finishGame() {
        _uiState.value = _uiState.value.copy(isFinished = true)
    }

    fun newQuestion(total: Int? = null) {
        _uiState.value = _uiState.value.copy(
            first = null,
            second = null,
            total = total ?: (4..99).random(),
            isCorrect = null
        )
    }

    fun checkAnswer() {
        val currentDualRowsState = _uiState.value
        if (currentDualRowsState.first != null && currentDualRowsState.second != null && currentDualRowsState.total != null) {
            val isCorrect =
                currentDualRowsState.first + currentDualRowsState.second == currentDualRowsState.total
            _uiState.value = currentDualRowsState.copy(
                isCorrect = isCorrect,
                correctCount = currentDualRowsState.correctCount + if (isCorrect) 1 else 0
            )
        }
    }
}
