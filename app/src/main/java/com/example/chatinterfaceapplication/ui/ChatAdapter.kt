package com.example.chatinterfaceapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatinterfaceapplication.R
import com.example.chatinterfaceapplication.data.model.ChatMessage

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    private var items = listOf<ChatMessage>()

    fun setItems(list: List<ChatMessage>) {
        items = list
        notifyDataSetChanged()
    }

    inner class ChatViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(msg: ChatMessage) {
            view.findViewById<TextView>(R.id.messageText).text = msg.message
            view.findViewById<TextView>(R.id.botName).text = msg.botName
            view.findViewById<ImageView>(R.id.statusIcon)
                .setImageResource(if (msg.isSent) R.drawable.ic_check else R.drawable.ic_error)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
