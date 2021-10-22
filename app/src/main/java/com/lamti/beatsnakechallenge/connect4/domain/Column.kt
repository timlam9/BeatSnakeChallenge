package com.lamti.beatsnakechallenge.connect4.domain

import kotlinx.serialization.Serializable

@Serializable
data class Column(val slots: List<Slot>)
