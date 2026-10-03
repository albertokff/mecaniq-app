package com.mecaniq.app.data.repository

import com.mecaniq.app.data.models.CriarVeiculoRequest
import com.mecaniq.app.data.models.VeiculoDTO
import com.mecaniq.app.network.ApiClient
import com.mecaniq.app.network.VeiculoApi
import com.mecaniq.app.network.createVeiculoApi

class VeiculoRepositoryImpl(
    private val veiculoApi: VeiculoApi = ApiClient.ktorfit.createVeiculoApi()
) : VeiculoRepository {
    override suspend fun buscarPorPlaca(placa: String): Result<VeiculoDTO?> {
        return runCatching {
            veiculoApi.buscarPorPlaca(placa)
        }
    }

    override suspend fun cadastrarVeiculo(request: CriarVeiculoRequest): Result<Boolean> {
        return runCatching {
            veiculoApi.cadastrarVeiculo(request)
        }
    }
}
