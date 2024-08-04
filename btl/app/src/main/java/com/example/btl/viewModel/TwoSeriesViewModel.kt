package com.example.btl.viewModel

import androidx.lifecycle.ViewModel
import com.example.btl.data.NumberState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

private const val SIZE = 12

data class TwoSeriesState(
    val firstNumbers: List<NumberState>,
    val secondNumbers: List<NumberState>,
    val targetSum: Int,
    val firstSelect: Int?,
    val secondSelect: Int?,
    val isFinished: Boolean
)
class TwoSeriesViewModel : ViewModel() {
    private val _firstNumbers = MutableStateFlow<List<NumberState>>(emptyList())
    val firstNumbers: StateFlow<List<NumberState>> get() = _firstNumbers

    private val _secondNumbers = MutableStateFlow<List<NumberState>>(emptyList())
    val secondNumbers: StateFlow<List<NumberState>> get() = _secondNumbers

    private val _targetSum = MutableStateFlow<Int>(0)
    val targetSum: StateFlow<Int> get() = _targetSum

    private val _firstSelect: MutableStateFlow<Int?> = MutableStateFlow(null)
    val firstSelect: StateFlow<Int?> get() = _firstSelect

    private val _secondSelect: MutableStateFlow<Int?> = MutableStateFlow(null)
    val secondSelect: StateFlow<Int?> get() = _secondSelect

    private val _isFinished = MutableStateFlow<Boolean>(false)
    val isFinished: StateFlow<Boolean> get() = _isFinished


    init {
        reset(SIZE, (1..10).random())
    }

    private fun generateNumbers(size: Int = SIZE, range: IntRange = 1..10) {
        val numbers1 = List(size) { range.random() }
        val numberStates1 = numbers1.mapIndexed { index, number ->
            NumberState(number = number, isMatched = false, index = index)
        }
        _firstNumbers.value = numberStates1

        val numbers2 = List(size) { range.random() }
        val numberStates2 = numbers2.mapIndexed { index, number ->
            NumberState(number = number, isMatched = false, index = index)
        }
        _secondNumbers.value = numberStates2
    }

    fun selectFirstNumber(index: Int) {
        if (_firstSelect.value == index) {
            _firstSelect.value = null
        } else {
            _firstSelect.value = index
        }
        checkMatch()
    }

    fun selectSecondNumber(index: Int) {
        if (_secondSelect.value == index) {
            _secondSelect.value = null
        } else {
            _secondSelect.value = index
        }
        checkMatch()
    }

    private fun checkMatch() {
        val firstIndex = _firstSelect.value
        val secondIndex = _secondSelect.value

        if (firstIndex != null && secondIndex != null) {
            val firstNumber = _firstNumbers.value[firstIndex]
            val secondNumber = _secondNumbers.value[secondIndex]

            if (firstNumber.number + secondNumber.number == _targetSum.value) {
                updateNumberState(_firstNumbers, firstIndex)
                updateNumberState(_secondNumbers, secondIndex)
            }
            _firstSelect.value = null
            _secondSelect.value = null
            _isFinished.value = isGameFinished()
        }
    }

    private fun updateNumberState(
        stateFlow: MutableStateFlow<List<NumberState>>,
        index: Int,
        isMatched: Boolean = true
    ) {
        val currentNumbers = stateFlow.value
        if (index < 0 || index >= currentNumbers.size) return

        val currentNumber = currentNumbers[index]
        val updatedNumber = currentNumber.copy(isMatched = isMatched)
        stateFlow.update { numbers ->
            numbers.toMutableList().apply { this[index] = updatedNumber }
        }
    }

    private fun isGameFinished(): Boolean {
        val firstAvailableNumbers = _firstNumbers.value.filterNot { it.isMatched }
        val secondAvailableNumbers = _secondNumbers.value.filterNot { it.isMatched }

        if (firstAvailableNumbers.isEmpty() || secondAvailableNumbers.isEmpty()) return true

        for (first in firstAvailableNumbers) {
            for (second in secondAvailableNumbers) {
                if (first.number + second.number == _targetSum.value) {
                    return false
                }
            }
        }
        return true
    }

    fun reset(size: Int = SIZE, total: Int?) {
        _targetSum.value = total ?: (10..20).random()
        generateNumbers(size = size, range = 1..<_targetSum.value)
        _firstSelect.value = null
        _secondSelect.value = null
        _isFinished.value = false
    }
}
