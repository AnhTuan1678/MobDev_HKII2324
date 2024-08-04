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
    private val _state = MutableStateFlow(ConnectSumState())
    val state: StateFlow<ConnectSumState> get() = _state

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
        _state.update { it.copy(numbers = nodes, row = row) }
    }

    fun selectNumber(index: Int) {
        val currentState = _state.value
        val currentSelected = currentState.numbers.getOrNull(index) ?: return
        val selected = currentState.selected
        if (currentSelected.isMatched) return

        if (selected == null) {
            _state.update { it.copy(selected = index) }
        } else {
            val selectedNumber = currentState.numbers.getOrNull(selected) ?: return
            if (selectedNumber.index != currentSelected.index) {
                val ans: Pair<List<Node>, Int>? = match(selectedNumber, currentSelected)
                if (ans != null && selectedNumber.number + currentSelected.number == currentState.targetSum) {
                    viewModelScope.launch {
                        _state.update { it.copy(path = ans.first) }
                        delay(1000)
                        _state.update { it.copy(path = emptyList()) }
                        updateNumberState(selected)
                        updateNumberState(index)
                        _state.update { state ->
                            state.copy(score = state.score + 10, isFinished = isGameFinished())
                        }
                    }
                } else {
                    _state.update { state ->
                        val newScore = (state.score - 5).coerceAtLeast(0)
                        state.copy(score = newScore)
                    }
                }
            }
            _state.update { it.copy(selected = null) }
        }
    }

    private fun match(from: NumberState, to: NumberState): Pair<List<Node>, Int>? {
        val currentState = _state.value
        val grid = Array(currentState.row) { IntArray(currentState.row) }
        for (i in currentState.numbers.indices) {
            val number = currentState.numbers[i]
            grid[number.x][number.y] = if (number.isMatched) 0 else 1
        }

        val start = Node(from.x, from.y, null, null)
        val goal = Node(to.x, to.y, null, null)

        return aStarPokemon(grid, start, goal)
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
                    val ans: Pair<List<Node>, Int>? =
                        match(availableNumbers[i], availableNumbers[j])
                    if (ans != null) {
                        Log.d(
                            "BTL_MOB_DEV",
                            "Match: ${availableNumbers[i]} and ${availableNumbers[j]}"
                        )
                        return false
                    }
                }
            }
        }
        return true
    }

    fun reset(row: Int, column: Int, total: Int?) {
        val targetSum = total ?: (10..20).random()
        generateNumbers(row, column, 1 until targetSum)
        _state.update {
            it.copy(
                targetSum = targetSum,
                selected = null,
                isFinished = false,
                score = 0,
                path = emptyList()
            )
        }
    }
}
