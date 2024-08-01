package com.example.btl.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Node(
    val value: Int,
    val x: Int,
    val y: Int,
    val isHidden: Boolean = false
)

class ConnectSumViewModel : ViewModel() {
    private val _nodes = MutableStateFlow<List<Node>>(emptyList())
    val nodes: StateFlow<List<Node>> get() = _nodes

    private val _total = MutableStateFlow(5)
    val total: StateFlow<Int> get() = _total

    private val _selectedNodes = MutableStateFlow<List<Node>>(emptyList())

    private val _isCorrect = MutableStateFlow(false)
    val isCorrect: StateFlow<Boolean> get() = _isCorrect

    init {
        generateNodes(5, 5, (0..39).toList())
    }

    fun selectNode(index: Int) {
        val node = _nodes.value[index]
        if (node.isHidden) return

        val selected = _selectedNodes.value.toMutableList()
        if (node in selected) {
            selected.remove(node)
        } else {
            selected.add(node)
        }
        _selectedNodes.value = selected

        if (selected.size == 2) {
            checkAnswer()
            _selectedNodes.value = emptyList()
        }

        Log.d("ConnectSumViewModel", "selectNode: ${_selectedNodes.value}")
    }


    private fun checkAnswer() {
        val selected = _selectedNodes.value
        val sum = selected.sumOf { it.value }

        // Kiểm tra câu trả lời
        _isCorrect.value = (sum == _total.value && canConnectWithThreeLines(selected))

        if (_isCorrect.value) {
            // Tạo danh sách các nút mới với các nút được chọn bị ẩn
            val updatedNodes = _nodes.value.map { node ->
                if (selected.contains(node)) {
                    // Thay vì thay đổi thuộc tính isHidden, loại bỏ nút ra khỏi danh sách
                    node.copy(isHidden = true)
                } else {
                    node
                }
            }
            _nodes.value = updatedNodes
        }

        Log.d("ConnectSumViewModel", "checkAnswer: ${_isCorrect.value}")
    }


    private fun canConnectWithThreeLines(nodes: List<Node>): Boolean {
        if (nodes.size < 2) return false

        val start = nodes.first()
        val end = nodes.last()

        val visited = mutableSetOf<Node>()
        return dfs(start, end, visited, 0)
    }

    private fun dfs(current: Node, target: Node, visited: MutableSet<Node>, turns: Int): Boolean {
        if (turns > 3) return false
        if (current == target) return true

        visited.add(current)

        // Duyệt qua tất cả các hướng (trái, phải, lên, xuống)
        val directions = listOf(
            current.copy(x = current.x - 1),
            current.copy(x = current.x + 1),
            current.copy(y = current.y - 1),
            current.copy(y = current.y + 1)
        )

        for (next in directions) {
            val nextNode = _nodes.value.find { it.x == next.x && it.y == next.y }
            if (nextNode != null && nextNode !in visited && isValidMove(current, nextNode)) {
                if (dfs(nextNode, target, visited, turns + 1)) {
                    return true
                }
            }
        }

        visited.remove(current)
        return false
    }

    private fun isValidMove(from: Node, to: Node): Boolean {
        // Kiểm tra điều kiện di chuyển hợp lệ giữa hai Node
        return to.x >= 0 && to.y >= 0 && !to.isHidden
    }

    private fun generateNodes(x: Int, y: Int, arr: List<Int>) {
        val nodes = mutableListOf<Node>()
        for (i in 0 until x) {
            for (j in 0 until y) {
                val value = (1..4).random()
                nodes.add(Node(value, i, j, false))
            }
        }
        _nodes.value = nodes
    }

    fun reset() {
        generateNodes(5, 5, (0..39).toList())
        _selectedNodes.value = emptyList()
        _isCorrect.value = false
    }
}
