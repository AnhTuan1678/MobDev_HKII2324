package com.example.btl.viewModel

import androidx.lifecycle.ViewModel
import com.example.btl.algorithms.Node
import com.example.btl.algorithms.aStarPokemon
import com.example.btl.data.NumberState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

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

    private val _path = mutableListOf<Node>()
    val path: List<Node> get() = _path

    init {
        generateNumbers(5, 5, 1..4)
        _targetSum.value = (5..6).random() // Ví dụ tổng mục tiêu giữa 5 và 13
        _isFinished.value = false
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
            if (
                selectedNumber.index != currentSelected.index
                && selectedNumber.number + currentSelected.number == _targetSum.value
            ) {
                val ans: Pair<List<Node>, Int>? = match(selectedNumber, currentSelected)
                if (ans != null) {
                    _path.clear()
                    _path.addAll(ans.first)
                    val turnCount = ans.second
                    updateNumberState(selected)
                    updateNumberState(index)
                }
            }
            _selected.value = null
        }
//        _isFinished.value = isGameFinished()
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
        return false
    }

    fun reset(row: Int, column: Int, total: Int?) {
        generateNumbers(row, column, 1..4)
        _targetSum.value = total ?: (10..20).random()
        _selected.value = null
        _isFinished.value = false
    }
}
