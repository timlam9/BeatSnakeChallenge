package com.lamti.beatsnakechallenge.snake.domain


private const val FAST_SPEED: Long = 100
private const val NORMAL_SPEED: Long = 300
private const val SLOW_SPEED: Long = 600

enum class SnakeSpeed(val value: Long) {

    Slow(SLOW_SPEED),
    Normal(NORMAL_SPEED),
    Fast(FAST_SPEED)

}
