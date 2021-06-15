package com.lamti.beatsnakechallenge


data class Cell(
    val type: Type,
    val x: Int,
    val y: Int
) {

    enum class Type {
        Empty,
        Passenger,
        DriverHead,
        DriverBody
    }

}
