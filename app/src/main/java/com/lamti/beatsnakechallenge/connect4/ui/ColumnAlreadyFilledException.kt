package com.lamti.beatsnakechallenge.connect4.ui

object ColumnAlreadyFilledException : Exception() {

    override val message: String
        get() = "Cannot select an already filled board"

}
