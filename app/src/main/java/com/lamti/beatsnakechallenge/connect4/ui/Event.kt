package com.lamti.beatsnakechallenge.connect4.ui

sealed class Event {

    data class OnColumnClicked(val index: Int): Event()

    object OnRestartClicked : Event()

}
