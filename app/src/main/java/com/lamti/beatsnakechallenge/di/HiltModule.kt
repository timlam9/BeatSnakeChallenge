package com.lamti.beatsnakechallenge.di

import android.content.Context
import com.lamti.beatsnakechallenge.connect4.data.WebSocket
import com.lamti.beatsnakechallenge.snake.data.SnakeRepository
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
    fun provideSnakeRepo(@ApplicationContext context: Context) = SnakeRepository(providePreferences(context))

    @Singleton
    @Provides
    fun providePreferences(context: Context): SnakePreferences =
        SnakePreferences(
            context.getSharedPreferences(
                SnakePreferences.SNAKE_PREFERENCES,
                Context.MODE_PRIVATE
            )
        )

    @Singleton
    @Provides
    fun provideGame(): Game = Game(11, 9)

    @Singleton
    @Provides
    fun provideWebSocket(@ApplicationContext context: Context): WebSocket = WebSocket(providePreferences(context))

}

