package com.mecaniq.app.data.repository

import com.mecaniq.app.data.models.OrdemServicoDTO

interface OrdemServicoRepository {
    suspend fun criarOS(os: OrdemServicoDTO): Result<OrdemServicoDTO>
    suspend fun buscarPorVeiculo(veiculoId: String): Result<List<OrdemServicoDTO>>
}