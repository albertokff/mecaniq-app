package com.mecaniq.app.data.repository

import com.mecaniq.app.data.models.AlertaPreventivoDTO
import com.mecaniq.app.network.AlertaApi
import com.mecaniq.app.network.ApiClient
import com.mecaniq.app.network.createAlertaApi

class AlertaRepositoryImpl(
    private val api: AlertaApi = ApiClient.ktorfit.createAlertaApi()
) : AlertaRepository {
    override suspend fun buscarAlertas(veiculoId: String): Result<List<AlertaPreventivoDTO>> {
        return runCatching { api.buscarAlertasPorVeiculo(veiculoId) }
    }
}