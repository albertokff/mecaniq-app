package com.mecaniq.app.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ClienteDTO(
    val id: String,
    val nome: String,
    val telefone: String,
    val email: String? = null
)
