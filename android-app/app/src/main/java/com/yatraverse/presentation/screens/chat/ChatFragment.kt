package com.yatraverse.presentation.screens.chat

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yatraverse.R
import com.yatraverse.data.api.RetrofitClient
import com.yatraverse.data.local.SessionManager
import com.yatraverse.data.models.ChatRequest
import kotlinx.coroutines.launch

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val adapter = ChatAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = view.findViewById<RecyclerView>(R.id.rvChat)
        val input = view.findViewById<EditText>(R.id.etInput)
        val send = view.findViewById<Button>(R.id.btnSend)
        val typing = view.findViewById<TextView>(R.id.tvTyping)
        val title = view.findViewById<TextView>(R.id.tvChatTitle)
        val back = view.findViewById<Button>(R.id.btnChatBack)

        val city = arguments?.getString("city")?.takeIf { it.isNotBlank() }

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        title.text = if (city == null) "Ask YatraVerse" else "Ask about $city"
        adapter.add(ChatMessage("Hi! Ask me anything about heritage destinations.", false))

        back.setOnClickListener { findNavController().popBackStack() }

        send.setOnClickListener {
            val question = input.text.toString().trim()
            if (question.isEmpty()) return@setOnClickListener

            val token = SessionManager(requireContext()).getToken()
            if (token == null) {
                adapter.add(ChatMessage("Please log in first.", false))
                rv.scrollToPosition(adapter.itemCount - 1)
                return@setOnClickListener
            }

            input.setText("")
            adapter.add(ChatMessage(question, true))
            rv.scrollToPosition(adapter.itemCount - 1)
            send.isEnabled = false
            typing.visibility = View.VISIBLE

            viewLifecycleOwner.lifecycleScope.launch {
                try {
                    val response = RetrofitClient.apiService.chat(
                        "Bearer $token",
                        ChatRequest(question, city)
                    )
                    val body = response.body()
                    if (response.isSuccessful && body != null) {
                        val src = body.sources.orEmpty()
                            .map { "${it.destination}: ${it.section}" }
                            .distinct()
                            .take(3)
                            .joinToString(" • ")
                        adapter.add(ChatMessage(body.answer, false, src.ifEmpty { null }))
                    } else {
                        val msg = when (response.code()) {
                            401, 403 -> "Your session expired. Please log in again."
                            502, 503 -> "The AI service is offline. Please try again later."
                            else -> "Something went wrong (${response.code()})."
                        }
                        adapter.add(ChatMessage(msg, false))
                    }
                } catch (e: Exception) {
                    adapter.add(ChatMessage("Network error: ${e.message}", false))
                } finally {
                    typing.visibility = View.GONE
                    send.isEnabled = true
                    rv.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }
    }
}