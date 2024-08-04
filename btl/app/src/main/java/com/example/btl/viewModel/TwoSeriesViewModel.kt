package com.example.btl.viewModel

import androidx.lifecycle.ViewModel
import com.example.btl.data.NumberState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

private const val SIZE = 12

data class TwoSeriesState(
    val firstNumbers: List<NumberState> = emptyList(),
    val secondNumbers: List<NumberState> = emptyList(),
    val targetSum: Int = 0,
    val firstSelect: Int? = null,
    val secondSelect: Int? = null,
    val isFinished: Boolean = false
)

class TwoSeriesViewModel : ViewModel() {
    private val _state = MutableStateFlow(TwoSeriesState())
    val state: StateFlow<TwoSeriesState> get() = _state

    init {
        reset(SIZE, (1..10).random())
    }

    private fun generateNumbers(size: Int = SIZE, range: IntRange = 1..10) {
        val numbers1 = List(size) { range.random() }
        val numberStates1 = numbers1.mapIndexed { index, number ->
            NumberState(number = number, isMatched = false, index = index)
        }
        val numbers2 = List(size) { range.random() }
        val numberStates2 = numbers2.mapIndexed { index, number ->
            NumberState(number = number, isMatched = false, index = index)
        }

        _state.update { it.copy(firstNumbers = numberStates1, secondNumbers = numberStates2) }
    }

    fun selectFirstNumber(index: Int) {
        _state.update { state ->
            state.copy(firstSelect = if (state.firstSelect == index) null else index)
        }
        checkMatch()
    }

    fun selectSecondNumber(index: Int) {
        _state.update { state ->
            state.copy(secondSelect = if (state.secondSelect == index) null else index)
        }
        checkMatch()
    }

    private fun checkMatch() {
        val firstIndex = _state.value.firstSelect
        val secondIndex = _state.value.secondSelect

        if (firstIndex != null && secondIndex != null) {
            val firstNumber = _state.value.firstNumbers[firstIndex]
            val secondNumber = _state.value.secondNumbers[secondIndex]

            if (firstNumber.number + secondNumber.number == _state.value.targetSum) {
                updateNumberState(firstIndex, isFirst = true)
                updateNumberState(secondIndex, isFirst = false)
            }
            _state.update { it.copy(firstSelect = null, secondSelect = null, isFinished = isGameFinished()) }
        }
    }

    private fun updateNumberState(index: Int, isFirst: Boolean) {
        val numbers = if (isFirst) _state.value.firstNumbers else _state.value.secondNumbers
        val updatedNumbers = numbers.toMutableList()
        updatedNumbers[index] = updatedNumbers[index].copy(isMatched = true)

        _state.update {
            if (isFirst) {
                it.copy(firstNumbers = updatedNumbers)
            } else {
                it.copy(secondNumbers = updatedNumbers)
            }
        }
    }

    private fun isGameFinished(): Boolean {
        val firstAvailableNumbers = _state.value.firstNumbers.filterNot { it.isMatched }
        val secondAvailableNumbers = _state.value.secondNumbers.filterNot { it.isMatched }

        if (firstAvailableNumbers.isEmpty() || secondAvailableNumbers.isEmpty()) return true

        for (first in firstAvailableNumbers) {
            for (second in secondAvailableNumbers) {
                if (first.number + second.number == _state.value.targetSum) {
                    return false
                }
            }
        }
        return true
    }

    fun reset(size: Int = SIZE, total: Int?) {
        val targetSum = total ?: (10..20).random()
        generateNumbers(size = size, range = 1 until targetSum)
        _state.update { it.copy(targetSum = targetSum, firstSelect = null, secondSelect = null, isFinished = false) }
    }
}
