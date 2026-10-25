package com.mecaniq.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mecaniq.app.domain.service.AuthService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authService: AuthService
) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState())
    val uiState : StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun processIntent(intent: LoginUiIntent) {
        when (intent) {
            is LoginUiIntent.OnEmailChanged -> _uiState.update { it.copy(email = intent.email) }
            is LoginUiIntent.OnSenhaChanged -> _uiState.update { it.copy(senha = intent.senha) }
            is LoginUiIntent.OnNomeOficinaChanged -> _uiState.update { it.copy(nomeOficina = intent.nomeOficina) }
            is LoginUiIntent.OnNomeAdminChanged -> _uiState.update { it.copy(nomeAdmin = intent.nomeAdmin) }
            is LoginUiIntent.OnTelefoneChanged -> _uiState.update { it.copy(telefone = intent.telefone) }

            is LoginUiIntent.ToggleRegisterMode -> _uiState.update { it.copy(isRegisterMode = !it.isRegisterMode, errorMessage = null) }
            is LoginUiIntent.ClearError -> _uiState.update { it.copy(errorMessage = null) }
            is LoginUiIntent.Submit -> executeAuth()
        }
    }

    private fun executeAuth() {
        val currentState = _uiState.value

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val result = if (currentState.isRegisterMode) {
                authService.executeRegister(
                    nomeOficina = currentState.nomeOficina,
                    nomeAdmin = currentState.nomeAdmin,
                    telefone = currentState.telefone,
                    email = currentState.email,
                    senha = currentState.senha
                )
            } else {
                authService.executeLogin(
                    email = currentState.email,
                    senha = currentState.senha
                )
            }

            result.onSuccess { session ->
                _uiState.update { it.copy(isLoading = false, sessionSuccess = session) }
            }.onFailure { exception ->
                _uiState.update { it.copy(isLoading = false, errorMessage = exception.message ?: "Erro ao autenticar.") }
            }
        }
    }
}