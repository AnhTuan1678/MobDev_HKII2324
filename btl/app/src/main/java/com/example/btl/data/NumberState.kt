package com.example.btl.data

data class NumberState(
    val number: Int,
    val isMatched: Boolean,
    val index: Int,
    val x: Int = 0,
    val y: Int = 0
)