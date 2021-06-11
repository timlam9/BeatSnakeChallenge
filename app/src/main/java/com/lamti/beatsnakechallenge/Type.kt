package com.lamti.beatsnakechallenge


data class Cell(
    val type: Type,
    val index: Int
) {

    enum class Type {
        Empty,
        Passenger,
        DriverHead,
        DriverBody
    }

}
