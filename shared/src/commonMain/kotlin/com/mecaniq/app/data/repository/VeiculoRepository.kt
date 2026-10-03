package com.mecaniq.app.data.repository

import com.mecaniq.app.data.models.CriarVeiculoRequest
import com.mecaniq.app.data.models.VeiculoDTO

interface VeiculoRepository {
    suspend fun buscarPorPlaca(placa: String): Result<VeiculoDTO?>
    suspend fun cadastrarVeiculo(request: CriarVeiculoRequest): Result<Boolean>
}
