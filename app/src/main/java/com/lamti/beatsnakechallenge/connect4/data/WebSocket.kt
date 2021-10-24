package com.lamti.beatsnakechallenge.connect4.data

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

class WebSocket {

    private lateinit var webSocket: WebSocket

    private val _messages = MutableSharedFlow<SocketMessage>()
    val messages: SharedFlow<SocketMessage> = _messages

    fun start(coroutineScope: CoroutineScope) {
        val request: Request = Request.Builder().url(WEB_SOCKET_URL).build()
        val client = OkHttpClient()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onMessage(webSocket: WebSocket, text: String) {
                coroutineScope.launch(Dispatchers.IO) {
                    _messages.emit(Json.decodeFromString(SocketMessage.serializer(), text))
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

    fun restart(scope: CoroutineScope) {
        closeSocket()
        start(scope)
    }

    companion object {

        private const val WEB_SOCKET_URL = "ws://192.168.1.101:8080/scoreFour"
        private const val WEB_SOCKET_CLOSE_CODE = 1000

    }

}

