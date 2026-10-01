package com.yatraverse.data.models

data class Destination(
    val id: Long,
    val name: String,
    val description: String?,
    val location: String?,
    val imageUrl: String?
)