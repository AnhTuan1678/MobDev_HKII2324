package com.example.btl.viewModel

import androidx.lifecycle.ViewModel
import com.example.btl.data.NumberState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class NumberMatchState(
    val numbers: List<NumberState> = emptyList(),
    val targetSum: Int = 0,
    val selected: Int? = null,
    val isFinished: Boolean = false,
    val score: Int = 0
)

class NumberMatchViewModel : ViewModel() {
    private val _state = MutableStateFlow(NumberMatchState())
    val state: StateFlow<NumberMatchState> get() = _state

    init {
        reset(25, (5..13).random())
    }

    private fun generateNumbers(size: Int = 25, range: IntRange = 1..10) {
        val numbers = List(size) { range.random() }
        val numberStates = numbers.mapIndexed { index, number ->
            NumberState(number = number, isMatched = false, index = index)
        }
        _state.update { it.copy(numbers = numberStates) }
    }

    fun selectNumber(index: Int) {
        val currentState = _state.value
        val currentSelected = currentState.numbers.getOrNull(index) ?: return

        if (currentSelected.isMatched) return

        val selected = currentState.selected
        if (selected == null) {
            _state.update { it.copy(selected = index) }
        } else {
            val selectedNumber = currentState.numbers.getOrNull(selected) ?: return
            if (selectedNumber.index != currentSelected.index) {
                if (selectedNumber.number + currentSelected.number == currentState.targetSum) {
                    updateNumberState(selected)
                    updateNumberState(index)
                    _state.update { it.copy(score = currentState.score + 10) }
                } else {
                    val newScore = (currentState.score - 5).coerceAtLeast(0)
                    _state.update { it.copy(score = newScore) }
                }
            }
            _state.update { it.copy(selected = null) }
        }
        _state.update { it.copy(isFinished = isGameFinished()) }
    }

    private fun updateNumberState(index: Int, isMatched: Boolean = true) {
        _state.update { state ->
            val updatedNumbers = state.numbers.toMutableList().apply {
                this[index] = this[index].copy(isMatched = isMatched)
            }
            state.copy(numbers = updatedNumbers)
        }
    }

    private fun isGameFinished(): Boolean {
        val currentState = _state.value
        val availableNumbers = currentState.numbers.filterNot { it.isMatched }
        if (availableNumbers.size < 2) return true

        for (i in availableNumbers.indices) {
            for (j in i + 1 until availableNumbers.size) {
                if (availableNumbers[i].number + availableNumbers[j].number == currentState.targetSum) {
                    return false
                }
            }
        }
        return true
    }

    fun reset(size: Int, total: Int?) {
        val targetSum = total ?: (10..20).random()
        _state.update {
            it.copy(
                targetSum = targetSum,
                selected = null,
                isFinished = false,
                score = 0
            )
        }
        generateNumbers(size, 1 until targetSum)
    }
}
