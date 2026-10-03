package com.mecaniq.app.ui.alerta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mecaniq.app.data.repository.AlertaRepository
import com.mecaniq.app.data.repository.AlertaRepositoryImpl
import com.mecaniq.app.ui.alerta.AlertaContract.Intent
import com.mecaniq.app.ui.alerta.AlertaContract.UiEffect
import com.mecaniq.app.ui.alerta.AlertaContract.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AlertaViewModel(
    private val veiculoIdInicial: String = "",
    private val repository: AlertaRepository = AlertaRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState(veiculoId = veiculoIdInicial))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<UiEffect>()
    val uiEffect: SharedFlow<UiEffect> = _uiEffect.asSharedFlow()

    init {
        if (veiculoIdInicial.isNotBlank()) {
            carregarAlertas(veiculoIdInicial)
        }
    }

    fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.CarregarAlertas -> carregarAlertas(intent.veiculoId)
            is Intent.Recarregar -> carregarAlertas(_uiState.value.veiculoId)
        }
    }

    private fun carregarAlertas(veiculoId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, veiculoId = veiculoId, mensagemErro = null) }

            val result = repository.buscarAlertas(veiculoId)
            result.onSuccess { listaAlertas ->
                _uiState.update { state ->
                    state.copy(isLoading = false, alertas = listaAlertas)
                }
            }.onFailure { exception ->
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        mensagemErro = "Erro ao buscar alertas: ${exception.message}"
                    )
                }
            }
        }
    }
}