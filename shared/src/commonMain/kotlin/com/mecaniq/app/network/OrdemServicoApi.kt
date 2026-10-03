package com.mecaniq.app.network

import com.mecaniq.app.data.models.OrdemServicoDTO
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path

interface OrdemServicoApi {
    @POST("ordens-servico")
    suspend fun criarOrdemServico(@Body os: OrdemServicoDTO): OrdemServicoDTO

    @GET("ordens-servico/veiculo/{veiculoId}")
    suspend fun buscarPorVeiculo(@Path("veiculoId") veiculoId: String): List<OrdemServicoDTO>
}