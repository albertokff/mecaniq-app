package com.mecaniq.app.data.repository

import com.mecaniq.app.data.models.OrdemServicoDTO
import com.mecaniq.app.network.ApiClient
import com.mecaniq.app.network.OrdemServicoApi
import com.mecaniq.app.network.createOrdemServicoApi

class OrdemServicoRepositoryImpl(
    private val ordemServicoApi: OrdemServicoApi = ApiClient.ktorfit.createOrdemServicoApi()
) : OrdemServicoRepository {
    override suspend fun criarOS(os: OrdemServicoDTO): Result<OrdemServicoDTO> {
        return runCatching { ordemServicoApi.criarOrdemServico(os) }
    }

    override suspend fun buscarPorVeiculo(veiculoId: String): Result<List<OrdemServicoDTO>> {
        return runCatching { ordemServicoApi.buscarPorVeiculo(veiculoId) }
    }
}