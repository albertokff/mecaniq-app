package com.mecaniq.app.data.repository

import com.mecaniq.app.data.CriarVeiculoRequest
import com.mecaniq.app.data.VeiculoDTO

interface VeiculoRepository {
    suspend fun buscarPorPlaca(placa: String): Result<VeiculoDTO?>
    suspend fun cadastrarVeiculo(request: CriarVeiculoRequest): Result<Boolean>
}