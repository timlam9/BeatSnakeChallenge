package com.lamti.beatsnakechallenge.snake.data

import com.lamti.beatsnakechallenge.main.domain.RegisteredUser
import com.lamti.beatsnakechallenge.main.domain.RegistrationCredentials
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SnakeApi {

    @GET("users")
    suspend fun getUsers(): List<User>

    @POST("user")
    suspend fun updateUser(@Body user: User): String

    @POST("postUser")
    suspend fun register(@Body user: RegistrationCredentials): RegisteredUser

}
