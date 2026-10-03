package com.mecaniq.app.data

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

@Serializable
data class CriarVeiculoRequest(
    val clienteId: String,
    val placa: String,
    val modelo: String,
    val marca: String,
    val ano: Int,
    val kmAtual: Int
)

@Serializable
data class ItemOSDTO(
    val id: String,
    val descricao: String,
    val tipo: String,
    val quantidade: Int,
    val precoUnitario: Double
)

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