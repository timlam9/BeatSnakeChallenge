package com.lamti.beatsnakechallenge.snake.data

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface SnakeApi {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("user")
    suspend fun getUser(@Field("id") id: String): User

    @POST("user")
    suspend fun updateUser(@Body user: User): String

}
