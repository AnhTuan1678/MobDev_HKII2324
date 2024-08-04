package com.example.btl.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btl.algorithms.Node
import com.example.btl.algorithms.aStarPokemon
import com.example.btl.data.NumberState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ConnectSumState(
    val numbers: List<NumberState> = emptyList(),
    val targetSum: Int = 0,
    val selected: Int? = null,
    val isFinished: Boolean = false,
    val score: Int = 0,
    val row: Int = 5,
    val path: List<Node> = emptyList()
)
class ConnectSumViewModel : ViewModel() {
    // StateFlow để lưu trữ danh sách số
    private val _numbers = MutableStateFlow<List<NumberState>>(emptyList())
    val numbers: StateFlow<List<NumberState>> get() = _numbers

    // StateFlow để lưu trữ tổng mục tiêu
    private val _targetSum = MutableStateFlow(0)
    val targetSum: StateFlow<Int> get() = _targetSum

    // StateFlow để lưu trữ số được chọn
    private val _selected = MutableStateFlow<Int?>(null)
    val selected: StateFlow<Int?> get() = _selected

    private val _isFinished = MutableStateFlow(false)
    val isFinished: StateFlow<Boolean> get() = _isFinished

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> get() = _score

    private val _row = MutableStateFlow(5)
    val row: StateFlow<Int> get() = _row

    private val _path = MutableStateFlow<List<Node>>(emptyList())
    val path: StateFlow<List<Node>> get() = _path

    init {
        reset(5, 5, null)
    }

    private fun generateNumbers(row: Int, column: Int, range: IntRange = 1..5) {
        val nodes = mutableListOf<NumberState>()
        for (i in 0 until row) {
            for (j in 0 until column) {
                val value = range.random()
                nodes.add(
                    NumberState(
                        number = value,
                        index = i * column + j,
                        isMatched = false,
                        x = i,
                        y = j
                    )
                )
            }
        }
        _row.value = row
        _numbers.value = nodes
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
                val ans: Pair<List<Node>, Int>? = match(selectedNumber, currentSelected)
                if (ans != null
                    && selectedNumber.number + currentSelected.number == _targetSum.value
                ) {
                    viewModelScope.launch {
                        _path.value = ans.first
                        delay(1000)
                        _path.value = emptyList()
                        updateNumberState(selected)
                        updateNumberState(index)
                        _score.value += 10
                        _isFinished.value = isGameFinished()
                    }
                } else{
                    _score.value -= 5
                    _score.value = if (_score.value < 0) 0 else _score.value
                }
            }
            _selected.value = null
        }
    }

    private fun match(from: NumberState, to: NumberState): Pair<List<Node>, Int>? {
        val grid = Array(_row.value) { IntArray(_row.value) }
        for (i in _numbers.value.indices) {
            val number = _numbers.value[i]
            grid[number.x][number.y] = if (number.isMatched) 0 else 1
        }

        val start = Node(from.x, from.y, null, null)
        val goal = Node(to.x, to.y, null, null)

        return aStarPokemon(grid, start, goal)
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
//        Kiểm tra xem có cặp số nào có tổng bằng mục tiêu không
        for (i in _numbers.value.indices) {
            for (j in _numbers.value.indices) {
                if (i == j) continue
                val number1 = _numbers.value[i]
                val number2 = _numbers.value[j]
                if (number1.isMatched || number2.isMatched) continue
                if (number1.number + number2.number == _targetSum.value) {
//                    Kiểm tra Match
                    val ans: Pair<List<Node>, Int>? = match(number1, number2)
                    if (ans != null) {
                        Log.d("BTL_MOB_DEV", "Match: $number1 and $number2")
                        return false
                    }
                }
            }
        }
        return true
    }

    fun reset(row: Int, column: Int, total: Int?) {
        _targetSum.value = total ?: (10..20).random()
        generateNumbers(row, column, 1..<_targetSum.value)
        _selected.value = null
        _isFinished.value = isGameFinished()
        _score.value = 0
    }
}
