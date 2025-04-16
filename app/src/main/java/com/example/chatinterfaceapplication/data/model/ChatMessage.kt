package com.example.chatinterfaceapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class ChatMessage(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val botName: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isSent: Boolean = false
)
