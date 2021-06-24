package com.lamti.beatsnakechallenge.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ScoreManager(private val increment: Int) {

    private val _score: MutableStateFlow<Int> = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    fun score() {
        _score.value = _score.value + increment
    }

    fun reset() {
        _score.value = 0
    }

}