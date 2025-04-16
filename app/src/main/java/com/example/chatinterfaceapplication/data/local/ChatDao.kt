package com.example.chatinterfaceapplication.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.chatinterfaceapplication.data.model.ChatMessage

@Dao
interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(chat: ChatMessage)

    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    fun getAllMessages(): LiveData<List<ChatMessage>>

    @Query("SELECT * FROM messages WHERE isSent = 0")
    suspend fun getUnsentMessages(): List<ChatMessage>

    @Update
    suspend fun updateMessage(chat: ChatMessage)
}
