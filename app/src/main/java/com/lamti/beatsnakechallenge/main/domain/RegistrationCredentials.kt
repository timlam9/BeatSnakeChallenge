package com.lamti.beatsnakechallenge.main.domain

import kotlinx.serialization.Serializable

data class RegistrationCredentials(
    val name: String,
    val email: String,
    val password: String
)

data class RegisteredUser(
    val name: String,
    val email: String,
    val token: String
)

@Serializable
data class RefreshTokenCredentials(
    val name: String,
    val email: String
)
