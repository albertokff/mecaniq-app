package com.mecaniq.app.data.repository

import com.mecaniq.app.data.models.AlertaPreventivoDTO

interface AlertaRepository {
    suspend fun buscarAlertas(veiculoId: String): Result<List<AlertaPreventivoDTO>>
}