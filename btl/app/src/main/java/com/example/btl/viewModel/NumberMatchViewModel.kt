package com.example.btl.viewModel

import androidx.lifecycle.ViewModel
import com.example.btl.data.NumberState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NumberMatchViewModel : ViewModel() {
    // StateFlow để lưu trữ danh sách số
    private val _numbers = MutableStateFlow<List<NumberState>>(emptyList())
    val numbers: StateFlow<List<NumberState>> get() = _numbers

    // StateFlow để lưu trữ tổng mục tiêu
    private val _targetSum = MutableStateFlow<Int>(0)
    val targetSum: StateFlow<Int> get() = _targetSum

    // StateFlow để lưu trữ số được chọn
    private val _selected = MutableStateFlow<Int?>(null)
    val selected: StateFlow<Int?> get() = _selected

    private val _isFinished = MutableStateFlow<Boolean>(false)
    val isFinished: StateFlow<Boolean> get() = _isFinished

    private val _score = MutableStateFlow<Int>(0)
    val score: StateFlow<Int> get() = _score

    init {
        generateNumbers(25)
        _targetSum.value = (5..13).random() // Ví dụ tổng mục tiêu giữa 5 và 13
        _isFinished.value = false
    }

    private fun generateNumbers(size: Int = 25, range: IntRange = 1..10) {
        val numbers = List(size) { range.random() }
        val numberStates = numbers.mapIndexed { index, number ->
            NumberState(number = number, isMatched = false, index = index)
        }
        _numbers.value = numberStates
    }

    fun selectNumber(index: Int) {
        val currentSelected = _numbers.value.getOrNull(index) ?: return
        val selected = _selected.value
        if (currentSelected.isMatched) return

        if (selected == null) {
            _selected.value = index
        } else {
            val selectedNumber = _numbers.value.getOrNull(selected) ?: return
            if (selectedNumber.index != currentSelected.index) {
                if (selectedNumber.number + currentSelected.number == _targetSum.value) {
                    updateNumberState(selected)
                    updateNumberState(index)
                    _score.value += 10
                } else {
                    _score.value -= 5
                    _score.value = if (_score.value < 0) 0 else _score.value
                }
            }
            _selected.value = null
        }
        _isFinished.value = isGameFinished()
    }

    private fun updateNumberState(index: Int, isMatched: Boolean = true) {
        val currentNumbers = _numbers.value
        if (index < 0 || index >= currentNumbers.size) return

        val currentNumber = currentNumbers[index]
        val updatedNumber = currentNumber.copy(isMatched = isMatched)
        _numbers.update { numbers ->
            numbers.toMutableList().apply { this[index] = updatedNumber }
        }
    }

    private fun isGameFinished(): Boolean {
        val currentNumbers = _numbers.value
        val targetSum = _targetSum.value

        val availableNumbers = currentNumbers.filterNot { it.isMatched }
        if (availableNumbers.size < 2) return true

        for (i in availableNumbers.indices) {
            for (j in i + 1 until availableNumbers.size) {
                if (availableNumbers[i].number + availableNumbers[j].number == targetSum) {
                    return false
                }
            }
        }
        return true
    }

    fun reset(size: Int, total: Int?) {
        generateNumbers(size)
        _targetSum.value = total ?: (10..20).random()
        _selected.value = null
        _isFinished.value = false
    }
}
