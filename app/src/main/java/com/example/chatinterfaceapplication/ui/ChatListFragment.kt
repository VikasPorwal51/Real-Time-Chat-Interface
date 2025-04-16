package com.example.chatinterfaceapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatinterfaceapplication.R
import com.example.chatinterfaceapplication.utils.NetworkUtil
import com.example.chatinterfaceapplication.viewmodel.ChatViewModel

class ChatListFragment : Fragment() {
    private lateinit var viewModel: ChatViewModel
    private val adapter = ChatAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_chat_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[ChatViewModel::class.java]

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val errorText = view.findViewById<TextView>(R.id.errorText)
        if (NetworkUtil.isNetworkAvailable(requireContext())) {
            recyclerView.visibility = View.VISIBLE
            errorText.visibility = View.GONE
        } else {
            errorText.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            errorText.text = "No network available"
        }

        val input = view.findViewById<EditText>(R.id.inputMessage)
        val sendBtn = view.findViewById<ImageView>(R.id.btnSend)

        sendBtn.setOnClickListener {
            if (!input.text.isNullOrBlank()) {
                viewModel.sendMessage("SupportBot", input.text.toString())
                input.setText("")
            }
        }

        viewModel.messages.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                recyclerView.visibility = View.VISIBLE
                errorText.visibility = View.GONE
            } else {
                errorText.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                errorText.text = "No chat available"
            }


            adapter.setItems(it)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
