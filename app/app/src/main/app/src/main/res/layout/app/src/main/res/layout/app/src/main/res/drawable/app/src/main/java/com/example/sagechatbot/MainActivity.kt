package com.example.sagechatbot

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ChatAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var editMessage: EditText
    private lateinit var btnSend: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        editMessage = findViewById(R.id.editMessage)
        btnSend = findViewById(R.id.btnSend)

        adapter = ChatAdapter(mutableListOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnSend.setOnClickListener {
            val text = editMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                sendUserMessage(text)
                editMessage.setText("")
                botReply("You said: $text")
            }
        }
    }

    private fun sendUserMessage(text: String) {
        adapter.addMessage(Message(text, isUser = true))
        recyclerView.scrollToPosition(adapter.itemCount - 1)
    }

    private fun botReply(text: String) {
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.addMessage(Message(text, isUser = false))
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        }, 600)
    }
}
