package com.mecaniq.app.data.repository

import com.mecaniq.app.data.models.CriarVeiculoRequest
import com.mecaniq.app.data.models.VeiculoDTO
import com.mecaniq.app.network.ApiClient
import com.mecaniq.app.network.VeiculoApi
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException

class VeiculoRepositoryImpl(
    private val veiculoApi: VeiculoApi = ApiClient.veiculoApi
) : VeiculoRepository {
    override suspend fun buscarPorPlaca(placa: String): Result<VeiculoDTO?> {
        return runCatching {
            try {
                veiculoApi.buscarPorPlaca(placa)
            } catch (e: ClientRequestException) {
                if (e.response.status.value == 404) {
                    null // Placa não encontrada no banco (404) -> Retorna null com sucesso
                } else {
                    throw e
                }
            } catch (e: NoTransformationFoundException) {
                // Se o Ktor não conseguir transformar a resposta vazia/404 do servidor
                null
            }
        }
    }

    override suspend fun cadastrarVeiculo(request: CriarVeiculoRequest): Result<Boolean> {
        return runCatching {
            try {
                veiculoApi.cadastrarVeiculo(request)
            } catch (e: Exception) {
                // Loga a falha e impede que a tela trave em exceções não tratadas
                throw Exception("Falha ao cadastrar no servidor: ${e.message}")
            }
        }
    }
}
