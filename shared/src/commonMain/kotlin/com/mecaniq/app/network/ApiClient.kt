package com.mecaniq.app.network

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object ApiClient {
    const val BASE_URL = "http://192.168.0.8:8080/"

    val httpClient by lazy {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }

    val ktorfit: Ktorfit by lazy {
        Ktorfit.Builder()
            .baseUrl(BASE_URL)
            .httpClient(httpClient)
            .build()
    }

    // Instâncias Lazy das APIs
    val veiculoApi: VeiculoApi by lazy { ktorfit.createVeiculoApi() }
    val clienteApi: ClienteApi by lazy { ktorfit.createClienteApi() }
    val ordemServicoApi: OrdemServicoApi by lazy { ktorfit.createOrdemServicoApi() }
    val alertaApi: AlertaApi by lazy { ktorfit.createAlertaApi() }
}