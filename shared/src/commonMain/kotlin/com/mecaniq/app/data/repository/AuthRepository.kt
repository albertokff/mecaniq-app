package com.mecaniq.app.data.repository

import com.mecaniq.app.data.models.AuthResponse
import com.mecaniq.app.data.models.LoginRequest
import com.mecaniq.app.data.models.RegisterOficinaRequest

interface AuthRepository {
    suspend fun login(request: LoginRequest): Result<AuthResponse>
    suspend fun registerOficina(request: RegisterOficinaRequest): Result<AuthResponse>
}