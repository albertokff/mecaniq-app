package com.mecaniq.app.data.models

import kotlinx.serialization.Serializable

@Serializable
data class VeiculoDTO(
    val id: String,
    val clienteId: String,
    val placa: String,
    val modelo: String,
    val marca: String,
    val ano: Int,
    val kmAtual: Int
)
