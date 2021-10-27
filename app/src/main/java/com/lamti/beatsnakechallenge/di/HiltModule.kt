package com.lamti.beatsnakechallenge.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lamti.beatsnakechallenge.main.domain.RefreshTokenCredentials
import com.lamti.beatsnakechallenge.snake.data.RegisterApi
import com.lamti.beatsnakechallenge.snake.data.SnakeApi
import com.lamti.beatsnakechallenge.snake.domain.Game
import com.lamti.beatsnakechallenge.snake.ui.MediaPlayerManager
import com.lamti.beatsnakechallenge.snake.ui.SnakePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideMediaPlayer(@ActivityContext context: Context): MediaPlayerManager = MediaPlayerManager(context)

}

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Singleton
    @Provides
    fun provideGame(): Game = Game(11, 9)

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext context: Context): SnakePreferences =
        SnakePreferences(
            context.getSharedPreferences(
                SnakePreferences.SNAKE_PREFERENCES,
                Context.MODE_PRIVATE
            )
        )


    //        private const val BASE_URL = "https://beatsnake.herokuapp.com/"
    private const val BASE_URL = "http://192.168.1.101:8080/"

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @Singleton
    @Provides
    fun provideClientBuilder(@ApplicationContext context: Context): OkHttpClient.Builder = OkHttpClient()
        .newBuilder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(
            ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        )

    @Singleton
    @Provides
    fun provideRegisterApi(okhttpClientBuilder: OkHttpClient.Builder, gson: Gson): RegisterApi = Retrofit.Builder()
        .client(okhttpClientBuilder.build())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(RegisterApi::class.java)

    @Singleton
    @Provides
    fun provideApi(okhttpClient: OkHttpClient, gson: Gson): SnakeApi = Retrofit
        .Builder()
        .client(okhttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(SnakeApi::class.java)

    @Singleton
    @Provides
    fun provideHttpClient(
        @ApplicationContext context: Context,
        api: RegisterApi,
        preferences: SnakePreferences
    ): OkHttpClient = OkHttpClient().newBuilder()
        .authenticator { _, response ->
            runBlocking {
                return@runBlocking when (response.code) {
                    401 -> {
                        val credentials = RefreshTokenCredentials(
                            email = preferences.getEmail(),
                            name = preferences.getUsername()
                        )
                        val token: String = api.refreshToken(credentials).value

                        preferences.saveToken(token)
                        response.request.newBuilder().header("Authorization", "Bearer $token").build()
                    }
                    else -> response.request
                }
            }
        }
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${preferences.getAuthToken()}")
                .build()
            chain.proceed(newRequest)
        }.addInterceptor(
            ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        ).build()

}

