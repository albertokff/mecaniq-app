package com.mecaniq.app.data.models

import kotlinx.serialization.Serializable

@Serializable
data class AlertaPreventivoDTO(
    val veiculoId: String,
    val tipo: String,
    val descricao: String,
    val kmUltimaTroca: Int,
    val kmProximaTroca: Int,
    val dataProximaTrocaEstimada: String,
    val kmRestantes: Int,
    val statusAlerta: String
)
