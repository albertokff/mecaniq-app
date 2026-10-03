package com.mecaniq.app.network

import com.mecaniq.app.data.models.ClienteDTO
import com.mecaniq.app.data.models.CriarClienteRequest
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST

interface ClienteApi {
    @GET("clientes")
    suspend fun buscarTodos(): List<ClienteDTO>

    @POST("clientes")
    suspend fun cadastrarCliente(@Body request: CriarClienteRequest): Boolean
}