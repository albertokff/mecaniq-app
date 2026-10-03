package com.mecaniq.app.data.repository

import com.mecaniq.app.data.CriarVeiculoRequest
import com.mecaniq.app.data.VeiculoDTO
import com.mecaniq.app.data.remote.VeiculoRemoteDataSource

class VeiculoRepositoryImpl(
    private val remoteDataSource: VeiculoRemoteDataSource = VeiculoRemoteDataSource()
) : VeiculoRepository {
    override suspend fun buscarPorPlaca(placa: String): Result<VeiculoDTO?> {
        return runCatching {
            remoteDataSource.fetchVeiculoPorPlaca(placa)
        }
    }

    override suspend fun cadastrarVeiculo(request: CriarVeiculoRequest): Result<Boolean> {
        return runCatching {
            remoteDataSource.createVeiculo(request)
        }
    }
}