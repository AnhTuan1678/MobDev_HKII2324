package com.example.btl.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class NumberState(
    val number: Int,
    val isMatched: Boolean,
    val position: Int
)

class NumberMatchViewModel : ViewModel() {
    // State để lưu trữ danh sách số, tổng mục tiêu, số được chọn, và trạng thái số
    private val _numbers = MutableLiveData<List<NumberState>>()
    val numbers: LiveData<List<NumberState>> get() = _numbers

    private val _targetSum = MutableLiveData<Int>()
    val targetSum: LiveData<Int> get() = _targetSum

    private val _selected = MutableLiveData<Int?>(null)
    val selected: LiveData<Int?> get() = _selected

    init {
        generateNumbers()
        _targetSum.value = (5..13).random() // Ví dụ tổng mục tiêu giữa 10 và 20
    }

    private fun generateNumbers(size: Int = 30, range: IntRange = 1..10) {
        // Tạo danh sách số ngẫu nhiên
        val numbers = List(size) { range.random() }

        // Tạo trạng thái cho từng số
        val numberStates = numbers.mapIndexed { index, number ->
            NumberState(
                number = number,
                isMatched = false,
                position = index
            )
        }

        // Cập nhật giá trị của _numbers
        _numbers.value = numberStates
    }


    fun selectNumber(index: Int) {
        val currentSelected = _numbers.value?.get(index) ?: return
        val sel = _selected.value
        if (currentSelected.isMatched) return

        if (sel == null) {
            _selected.value = index
        } else {
            val selectedNumber = _numbers.value?.get(sel) ?: return
            if (selectedNumber.number + currentSelected.number == _targetSum.value && selectedNumber.position != currentSelected.position) {
                updateNumberState(sel)
                updateNumberState(index)
            }
            _selected.value = null
        }
    }

    private fun updateNumberState(index: Int, isMatched: Boolean = true) {
        // Lấy danh sách hiện tại
        val currentNumbers = _numbers.value ?: return

        // Kiểm tra xem chỉ số có hợp lệ không
        if (index < 0 || index >= currentNumbers.size) {
            return
        }

        // Lấy số hiện tại và cập nhật trạng thái
        val currentNumber = currentNumbers[index]
        val updatedNumber = currentNumber.copy(isMatched = isMatched)
        val updatedNumbers = currentNumbers.toMutableList().apply {
            this[index] = updatedNumber
        }

        // Cập nhật giá trị mới cho _numbers
        _numbers.value = updatedNumbers
    }

    fun isGameFinished(): Boolean {
        val currentNumbers = _numbers.value ?: return false
        val targetSum = _targetSum.value ?: return false

        // Kiểm tra điều kiện không còn số nào có thể ghép
        val availableNumbers = currentNumbers.filterNot { it.isMatched }
        if (availableNumbers.size < 2) {
            return true
        }

        // Kiểm tra nếu không còn cặp số nào có thể tạo ra tổng mục tiêu
        for (i in availableNumbers.indices) {
            for (j in i + 1 until availableNumbers.size) {
                if (availableNumbers[i].number + availableNumbers[j].number == targetSum) {
                    return false
                }
            }
        }

        // Nếu không tìm thấy cặp nào hợp lệ, trò chơi đã kết thúc
        return true
    }

    fun reset() {
        generateNumbers()
        _targetSum.value = (10..20).random()
        _selected.value = null
    }
}
