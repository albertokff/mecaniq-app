package com.mecaniq.app.network

import com.mecaniq.app.data.models.AuthResponse
import com.mecaniq.app.data.models.LoginRequest
import com.mecaniq.app.data.models.RegisterOficinaRequest
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Result<AuthResponse>

    @POST("auth/register")
    suspend fun registerOficina(@Body request: RegisterOficinaRequest): Result<AuthResponse>
}