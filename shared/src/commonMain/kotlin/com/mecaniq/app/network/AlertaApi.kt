package com.mecaniq.app.network

import com.mecaniq.app.data.models.AlertaPreventivoDTO
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

interface AlertaApi {
    @GET("veiculos/{id}/alertas")
    suspend fun buscarAlertasPorVeiculo(@Path("id") veiculoId: String): List<AlertaPreventivoDTO>
}