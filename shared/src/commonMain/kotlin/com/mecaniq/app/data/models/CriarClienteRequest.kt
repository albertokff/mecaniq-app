package com.mecaniq.app.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CriarClienteRequest(
    val nome: String,
    val telefone: String,
    val email: String? = null
)
