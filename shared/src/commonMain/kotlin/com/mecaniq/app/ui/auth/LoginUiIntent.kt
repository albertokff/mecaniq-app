package com.mecaniq.app.ui.auth

sealed interface LoginUiIntent {
    data class OnEmailChanged(val email: String) : LoginUiIntent
    data class OnSenhaChanged(val senha: String) : LoginUiIntent
    data class OnNomeOficinaChanged(val nomeOficina: String) : LoginUiIntent
    data class OnNomeAdminChanged(val nomeAdmin: String) : LoginUiIntent
    data class OnTelefoneChanged(val telefone: String) : LoginUiIntent

    object ToggleRegisterMode : LoginUiIntent
    object Submit : LoginUiIntent
    object ClearError : LoginUiIntent
}