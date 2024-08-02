package com.example.btl.algorithms

import android.util.Log
import java.util.PriorityQueue

data class Node(
    val x: Int,
    val y: Int,
    val parent: Node?,
    val direction: Int?,
    var g: Int = 0,
    var h: Int = 0
) : Comparable<Node> {
    val f: Int
        get() = g + h

    override fun compareTo(other: Node): Int {
        return this.f.compareTo(other.f)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Node
        return x == other.x && y == other.y
    }

    override fun hashCode(): Int {
        return 31 * x + y
    }
}

fun aStar(grid: Array<IntArray>, start: Node, goal: Node): Pair<List<Node>, Int> {
    val dx = arrayOf(-1, 1, 0, 0)
    val dy = arrayOf(0, 0, -1, 1)
    val n = grid.size
    val openList = PriorityQueue<Node>()
    val closedList = mutableSetOf<Node>()
    openList.add(start)

    while (openList.isNotEmpty()) {
        val current = openList.poll() ?: break

        if (current.x == goal.x && current.y == goal.y) {
            val path = reconstructPath(current)
            val turnCount = countTurns(path)
            return Pair(path, turnCount)
        }

        closedList.add(current)

        for (i in 0..3) {
            val nx = current.x + dx[i]
            val ny = current.y + dy[i]

            if (nx in 0 until n && ny in 0 until n) {
                // Chỉ kiểm tra chướng ngại vật khi không phải là đích đến
                if (grid[nx][ny] != 1 || (nx == goal.x && ny == goal.y)) {
                    val neighbor = Node(nx, ny, current, i)

                    if (closedList.contains(neighbor)) {
                        continue
                    }

                    val turnPenalty =
                        if (current.direction == null || current.direction == i) 0 else 1
                    neighbor.g = current.g + 1 + turnPenalty
                    neighbor.h = Math.abs(nx - goal.x) + Math.abs(ny - goal.y)

                    if (!openList.contains(neighbor) || neighbor.g < current.g) {
                        openList.add(neighbor)
                    }
                }
            }
        }
    }

    return Pair(emptyList(), -1)
}

fun reconstructPath(node: Node?): List<Node> {
    val path = mutableListOf<Node>()
    var current = node
    while (current != null) {
        path.add(current)
        current = current.parent
    }
    path.reverse()
    return path
}

fun countTurns(path: List<Node>): Int {
    if (path.isEmpty()) return 0
    var turns = 0
    var prevDirection: Int? = path[0].direction
    for (i in 1 until path.size) {
        val currentDirection = path[i].direction
        if (currentDirection != prevDirection) {
            turns++
        }
        prevDirection = currentDirection
    }
    return turns
}

fun aStarPokemon(grid: Array<IntArray>, start: Node, goal: Node): Pair<List<Node>, Int>? {
    val matrix: Array<IntArray> = Array(grid.size + 2) { IntArray(grid[0].size + 2) }
    for (i in grid.indices) {
        for (j in 0 until grid[0].size) {
            matrix[i + 1][j + 1] = grid[i][j]
        }
    }

    val newStart = Node(start.x + 1, start.y + 1, null, null)
    val newGoal = Node(goal.x + 1, goal.y + 1, null, null)
    val (path, turnCount) = aStar(matrix, newStart, newGoal)
    if (path.isEmpty()) return null
    if (turnCount == -1 || turnCount > 3) return null
    return Pair(path, turnCount)
}