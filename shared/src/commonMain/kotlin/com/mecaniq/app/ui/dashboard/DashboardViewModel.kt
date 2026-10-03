package com.mecaniq.app.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardContract.UiState())
    val uiState: StateFlow<DashboardContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<DashboardContract.UiEffect>()
    val uiEffect: SharedFlow<DashboardContract.UiEffect> = _uiEffect.asSharedFlow()

    init {

    }

    fun handleIntent(intent: DashboardContract.Intent) {
        viewModelScope.launch {
            when (intent) {
                is DashboardContract.Intent.CarregarDados -> carregarResumoDashboard()
                is DashboardContract.Intent.BuscarPlaca -> processarBuscaPlaca(intent.placa)
                is DashboardContract.Intent.NovaOSClicada -> _uiEffect.emit(DashboardContract.UiEffect.NavegarParaNovaOS())
            }
        }
    }

    private fun carregarResumoDashboard() {
        _uiState.update { it.copy(isLoading = true) }
        _uiState.update {
            it.copy(
                isLoading = false,
                totalOsAbertas = 10,
                alertasPendentes = 5
            )
        }
    }

    private suspend fun processarBuscaPlaca(placa: String) {
        if (placa.isBlank() || placa.length < 7) {
            _uiEffect.emit(DashboardContract.UiEffect.MostrarToast("Digite uma placa válida com 7 caracteres."))
            return
        }
        _uiEffect.emit(DashboardContract.UiEffect.NavegarParaVeiculo(placa))
    }
}