package com.example.chatinterfaceapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chatinterfaceapplication.data.local.ChatDatabase
import com.example.chatinterfaceapplication.data.model.ChatMessage
import com.example.chatinterfaceapplication.data.repository.ChatRepository
import com.example.chatinterfaceapplication.network.SocketManager
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class ChatViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = ChatDatabase.getDatabase(app).chatDao()
    private val repo = ChatRepository(dao)
    val messages = repo.allMessages

    val isConnected = MutableLiveData<Boolean>(false)
    val error = MutableLiveData<String?>()

    val network = MutableLiveData<Boolean>(true)

    init {
        connectSocket()
    }

    fun connectSocket() {
        SocketManager.connect(object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                isConnected.postValue(true)
                viewModelScope.launch {
                    repo.retryUnsentMessages()
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                isConnected.postValue(false)
                error.postValue("Socket connection failed.")
            }
        })
    }

    fun sendMessage(bot: String, text: String) {

        SocketManager.connect(object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                isConnected.postValue(true)
                val chat = ChatMessage(botName = bot, message = text, isSent = true)
                viewModelScope.launch {
                    repo.sendMessage(chat, isConnected.value == true)
                }

                network.postValue(true)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                isConnected.postValue(false)
                error.postValue("No network available")
                val chat = ChatMessage(botName = bot, message = text)
                viewModelScope.launch {
                    repo.sendMessage(chat, isConnected.value == true)
                }

                network.postValue(false)
            }
        })
    }

    fun retryMsg() {
        viewModelScope.launch {
            repo.retryUnsentMessages()
        }
    }
}
