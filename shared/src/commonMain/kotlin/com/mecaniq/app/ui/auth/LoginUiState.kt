package com.mecaniq.app.ui.auth

import com.mecaniq.app.data.models.AuthResponse

data class LoginUiState(
    val isRegisterMode: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val email: String = "",
    val senha: String = "",
    val nomeOficina: String = "",
    val nomeAdmin: String = "",
    val telefone: String = "",

    val sessionSuccess: AuthResponse? = null
)
