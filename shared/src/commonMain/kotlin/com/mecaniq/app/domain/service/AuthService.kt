package com.mecaniq.app.domain.service

import com.mecaniq.app.data.models.AuthResponse
import com.mecaniq.app.data.models.LoginRequest
import com.mecaniq.app.data.models.RegisterOficinaRequest
import com.mecaniq.app.data.repository.AuthRepository

class AuthService(
    private val authRepository: AuthRepository
) {
    suspend fun executeLogin(email: String, senha: String): Result<AuthResponse> {
        if (email.isBlank() || !email.contains("@")) {
            return Result.failure(Exception("Insira um e-mail válido."))
        }

        if (senha.length < 6) {
            return Result.failure(Exception("A senha deve conter pelo menos 6 caracteres."))
        }

        val request = LoginRequest(email, senha)
        return authRepository.login(request)
    }

    suspend fun executeRegister(nomeOficina: String, nomeAdmin: String, telefone: String, email: String, senha: String): Result<AuthResponse> {
        if (nomeOficina.isBlank() || nomeAdmin.isBlank()) {
            return Result.failure(Exception("Nome da oficina e do administrador são obrigatórios"))
        }

        if (email.isBlank() || !email.contains("@")) {
            return Result.failure(Exception("Insira um e-mail válido."))
        }

        if (senha.length < 6) {
            return Result.failure(Exception("A senha deve conter pelo menos 6 caracteres."))
        }

        val request = RegisterOficinaRequest(
            nomeOficina = nomeOficina,
            nomeAdmin = nomeAdmin,
            telefone = telefone,
            emailAdmin = email,
            senhaAdmin = senha
        )

        return authRepository.registerOficina(request)
    }
}