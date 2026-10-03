package com.mecaniq.app.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CriarVeiculoRequest(
    val clienteId: String,
    val placa: String,
    val modelo: String,
    val marca: String,
    val ano: Int,
    val kmAtual: Int
)
