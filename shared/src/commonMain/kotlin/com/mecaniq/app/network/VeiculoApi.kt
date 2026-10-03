package com.mecaniq.app.network

import com.mecaniq.app.data.models.CriarVeiculoRequest
import com.mecaniq.app.data.models.VeiculoDTO
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path

interface VeiculoApi {
    @GET("veiculos/placa/{placa}")
    suspend fun buscarPorPlaca(@Path("placa") placa: String): VeiculoDTO?

    @POST("veiculos")
    suspend fun cadastrarVeiculo(@Body request: CriarVeiculoRequest): Boolean
}