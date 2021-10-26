package com.lamti.beatsnakechallenge.main.domain

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
