package com.lamti.beatsnakechallenge.connect4.data

import com.lamti.beatsnakechallenge.connect4.data.SocketMessage.InBound
import com.lamti.beatsnakechallenge.di.SingletonModule.HEROKU_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class WebSocket @Inject constructor(private val client: OkHttpClient) {

    private lateinit var webSocket: WebSocket

    private val _messages = MutableSharedFlow<InBound>()
    val messages: SharedFlow<InBound> = _messages

    fun start(coroutineScope: CoroutineScope) {
        val request: Request = Request.Builder().url(WEB_SOCKET_URL).build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onMessage(webSocket: WebSocket, text: String) {
                coroutineScope.launch(Dispatchers.IO) {
                    _messages.emit(Json.decodeFromString(InBound.serializer(), text))
                }
                super.onMessage(webSocket, text)
            }

        })
        client.dispatcher.executorService.shutdown()
    }

    fun sendMessage(message: SocketMessage) {
        val json: JsonElement = Json.encodeToJsonElement(message)
        webSocket.send(json.toString())
    }

    fun closeSocket() {
        webSocket.close(WEB_SOCKET_CLOSE_CODE, null)
    }

    companion object {

        private const val WEB_SOCKET_URL = "ws://$HEROKU_URL/scoreFour"
        private const val WEB_SOCKET_CLOSE_CODE = 1000

    }

}

