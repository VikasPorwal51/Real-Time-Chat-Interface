package com.example.chatinterfaceapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewModelScope
import com.example.chatinterfaceapplication.network.SocketManager
import com.example.chatinterfaceapplication.ui.ChatListFragment
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load ChatListFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ChatListFragment())
            .commit()
    }
}