package com.lamti.beatsnakechallenge.snake.data

import com.lamti.beatsnakechallenge.connect4.domain.AuthToken
import com.lamti.beatsnakechallenge.main.domain.RefreshTokenCredentials
import com.lamti.beatsnakechallenge.main.domain.RegisteredUser
import com.lamti.beatsnakechallenge.main.domain.RegistrationCredentials
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {

    @POST("postUser")
    suspend fun register(@Body user: RegistrationCredentials): RegisteredUser

    @POST("refreshToken")
    suspend fun refreshToken(@Body user: RefreshTokenCredentials): AuthToken

}
