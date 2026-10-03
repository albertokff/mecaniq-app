package com.mecaniq.app.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ItemOSDTO(
    val id: String,
    val descricao: String,
    val tipo: String,
    val quantidade: Int,
    val precoUnitario: Double
)
