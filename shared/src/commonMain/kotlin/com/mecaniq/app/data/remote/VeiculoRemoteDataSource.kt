package com.mecaniq.app.data.remote

import com.mecaniq.app.data.CriarVeiculoRequest
import com.mecaniq.app.data.VeiculoDTO
import com.mecaniq.app.network.ApiClient
import com.mecaniq.app.network.VeiculoEndpoints
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class VeiculoRemoteDataSource(
    private val client: ApiClient = ApiClient
) {
    suspend fun fetchVeiculoPorPlaca(placa: String): VeiculoDTO? {
        val url = VeiculoEndpoints.buscarPorPlaca(placa)
        val response = client.httpClient.get(url)

        return if (response.status == HttpStatusCode.OK) {
            response.body<VeiculoDTO>()
        } else {
            null
        }
    }

    suspend fun createVeiculo(request: CriarVeiculoRequest): Boolean {
        val url = VeiculoEndpoints.criarVeiculo()
        val response = client.httpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.status == HttpStatusCode.Created || response.status == HttpStatusCode.OK
    }
}