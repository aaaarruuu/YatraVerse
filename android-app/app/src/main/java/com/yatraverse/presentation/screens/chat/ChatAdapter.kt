package com.yatraverse.presentation.screens.chat

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yatraverse.R

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val sources: String? = null
)

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.VH>() {

    private val items = mutableListOf<ChatMessage>()

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val root: LinearLayout = view as LinearLayout
        val message: TextView = view.findViewById(R.id.tvMessage)
        val sources: TextView = view.findViewById(R.id.tvSources)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return VH(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val m = items[position]
        val density = holder.itemView.resources.displayMetrics.density

        val bg = GradientDrawable().apply {
            cornerRadius = 18f * density
            setColor(Color.parseColor(if (m.isUser) "#1E6FD9" else "#EDEFF2"))
        }
        holder.message.background = bg
        holder.message.setTextColor(Color.parseColor(if (m.isUser) "#FFFFFF" else "#1B1B1B"))
        holder.message.text = m.text
        holder.root.gravity = if (m.isUser) Gravity.END else Gravity.START

        if (m.sources.isNullOrBlank()) {
            holder.sources.visibility = View.GONE
        } else {
            holder.sources.text = "Sources: ${m.sources}"
            holder.sources.visibility = View.VISIBLE
        }
    }

    fun add(message: ChatMessage) {
        items.add(message)
        notifyItemInserted(items.size - 1)
    }
}