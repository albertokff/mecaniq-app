package com.mecaniq.app.data.models

import kotlinx.serialization.Serializable

@Serializable
data class OrdemServicoDTO(
    val id: String,
    val oficinaId: String,
    val veiculoId: String,
    val status: String,
    val kmEntrada: Int,
    val valorTotal: Double,
    val observacoes: String?,
    val itens: List<ItemOSDTO>
)
