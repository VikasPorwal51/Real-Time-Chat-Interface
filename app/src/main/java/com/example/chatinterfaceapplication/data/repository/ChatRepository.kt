package com.example.chatinterfaceapplication.data.repository

import com.example.chatinterfaceapplication.data.local.ChatDao
import com.example.chatinterfaceapplication.data.model.ChatMessage
import com.example.chatinterfaceapplication.network.SocketManager

class ChatRepository(private val dao: ChatDao) {
    val allMessages = dao.getAllMessages()

    suspend fun sendMessage(chat: ChatMessage, isOnline: Boolean) {
        dao.insertMessage(chat)
        if (isOnline) {
            SocketManager.sendMessage(chat.message)
            dao.updateMessage(chat.copy(isSent = true))
        }
    }

    suspend fun retryUnsentMessages() {
        dao.getUnsentMessages().forEach {
            SocketManager.sendMessage(it.message)
            dao.updateMessage(it.copy(isSent = true))
        }
    }
}
