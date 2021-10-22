package com.lamti.beatsnakechallenge.connect4.domain

import kotlinx.serialization.Serializable

@Serializable
enum class Availability {
    Available,
    Player,
    Opponent
}
