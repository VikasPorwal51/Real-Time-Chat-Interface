package com.example.chatinterfaceapplication.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener

object SocketManager {
    private var webSocket: WebSocket? = null
    private var client: OkHttpClient? = null

    fun connect(listener: WebSocketListener) {
        client = OkHttpClient()
        val request = Request.Builder()
            .url("wss://demo.piesocket.com/v3/channel_123") // test socket URL
            .build()
        webSocket = client!!.newWebSocket(request, listener)
    }

    fun sendMessage(message: String) {
        val json = """
            {
                "message": "$message"
            }
        """.trimIndent()

        webSocket?.send(json)
    }

    fun close() {
        webSocket?.close(1000, "Closed by app")
        client?.dispatcher?.executorService?.shutdown()
    }
}
