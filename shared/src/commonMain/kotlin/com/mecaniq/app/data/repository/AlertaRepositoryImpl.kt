package com.mecaniq.app.data.repository

import com.mecaniq.app.data.models.AlertaPreventivoDTO
import com.mecaniq.app.network.AlertaApi
import com.mecaniq.app.network.ApiClient

class AlertaRepositoryImpl(
    private val api: AlertaApi = ApiClient.alertaApi
) : AlertaRepository {
    override suspend fun buscarAlertas(veiculoId: String): Result<List<AlertaPreventivoDTO>> {
        return runCatching { api.buscarAlertasPorVeiculo(veiculoId) }
    }
}