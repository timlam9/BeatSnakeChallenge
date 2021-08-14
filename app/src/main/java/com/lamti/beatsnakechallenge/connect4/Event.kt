package com.lamti.beatsnakechallenge.connect4

sealed class Event {

    data class OnColumnClicked(val index: Int): Event()

}
