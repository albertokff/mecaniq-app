package com.mecaniq.app.data.repository

import com.mecaniq.app.data.local.SessionManager
import com.mecaniq.app.data.models.AuthResponse
import com.mecaniq.app.data.models.LoginRequest
import com.mecaniq.app.data.models.RegisterOficinaRequest
import com.mecaniq.app.network.AuthApi

class AuthRepositoryImpl(
    private val apiService: AuthApi
) : AuthRepository {
    override suspend fun login(request: LoginRequest): Result<AuthResponse> {
        val result = apiService.login(request)

        result.onSuccess { session ->
            SessionManager.saveSession(session)
        }

        return result
    }

    override suspend fun registerOficina(request: RegisterOficinaRequest): Result<AuthResponse> {
        val result = apiService.registerOficina(request)

        result.onSuccess { session ->
            SessionManager.saveSession(session)
        }

        return result
    }
}