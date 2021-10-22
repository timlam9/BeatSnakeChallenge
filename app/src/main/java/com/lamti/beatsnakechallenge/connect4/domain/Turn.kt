package com.lamti.beatsnakechallenge.connect4.domain

import kotlinx.serialization.Serializable

@Serializable
enum class Turn {
    Player,
    Opponent;

    override fun toString(): String {
        return "$name's turn!"
    }

    fun next(): Turn = when (this) {
        Player -> Opponent
        Opponent -> Player
    }

}
