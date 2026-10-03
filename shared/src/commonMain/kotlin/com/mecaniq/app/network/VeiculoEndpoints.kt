package com.mecaniq.app.network

object VeiculoEndpoints {
    private const val VEICULOS = "${ApiClient.BASE_URL}/veiculos"

    fun buscarPorPlaca(placa: String) = "$VEICULOS/placa/$placa"
    fun criarVeiculo() = VEICULOS
}