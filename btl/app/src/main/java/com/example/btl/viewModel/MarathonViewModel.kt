package com.example.btl.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

data class Question(
    val addend1: Int?,
    val addend2: Int?,
    val sum: Int?,
    val answer: Int = 0,
    val selectableNumbers: List<Int> = emptyList()
)

data class MarathonState(
    val questions: List<Question> = emptyList(),
    val size: Int = 10,
    val currentQuestionIndex: Int = 0,
    val isFinished: Boolean = false,
    val score: Int = 0,
    val isCorrect: Boolean? = null,
    val correctCount: Int = 0
)

class MarathonViewModel : ViewModel() {
    private val _state = MutableStateFlow(
        MarathonState()
    )
    val state: MutableStateFlow<MarathonState> get() = _state

    init {
        reset()
    }

    fun reset(size: Int = 100) {
        generateQuestions(size)
        _state.value = _state.value.copy(
            currentQuestionIndex = 0,
            isFinished = false,
            score = 0,
            isCorrect = null
        )
    }

    private fun generateQuestions(size: Int = 100) {
        val questions = List(size) {
            var addend1: Int? = (1..10).random()
            var addend2: Int? = (1..10).random()
            var sum: Int? = addend1?.plus(addend2!!)
            val answer: Int

            val t = (1..3).random()
            when (t) {
                1 -> {
                    answer = addend1!!
                    addend1 = null
                }
                2 -> {
                    answer = addend2!!
                    addend2 = null
                }
                else -> {
                    answer = sum!!
                    sum = null
                }
            }
            val option = listOf(
                (1..10).random(),
                (1..10).random(),
                (1..10).random(),
                answer
            )
            option.shuffled()
            Question(addend1, addend2, sum, answer, option)
        }
        _state.value = _state.value.copy(questions = questions)

    }

    fun checkAnswer(answer: Int): Boolean {
        val currentQuestion = _state.value.questions[_state.value.currentQuestionIndex]
        val isCorrect = currentQuestion.answer == answer
        _state.value = _state.value.copy(
            isCorrect = isCorrect,
            score = _state.value.score + if (isCorrect) 10 else -5,
            correctCount = _state.value.correctCount + if (isCorrect) 1 else 0
        )
        return isCorrect
    }

    fun nextQuestion() {
        if (_state.value.currentQuestionIndex < _state.value.size - 1) {
            _state.value = _state.value.copy(
                currentQuestionIndex = _state.value.currentQuestionIndex + 1,
                isCorrect = null
            )
        } else {
            finish()
        }
    }

    fun finish() {
        _state.value = _state.value.copy(isFinished = true)
    }
}