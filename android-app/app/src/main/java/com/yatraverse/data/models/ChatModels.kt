package com.yatraverse.data.models

data class ChatRequest(
    val question: String,
    val city: String? = null
)

data class ChatSource(
    val destination: String,
    val city: String,
    val section: String,
    val score: Double
)

data class ChatResponse(
    val answer: String,
    val grounded: Boolean,
    val sources: List<ChatSource>?
)