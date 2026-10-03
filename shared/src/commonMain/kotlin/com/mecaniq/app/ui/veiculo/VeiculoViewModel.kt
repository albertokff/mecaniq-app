package com.mecaniq.app.ui.veiculo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mecaniq.app.data.models.CriarVeiculoRequest
import com.mecaniq.app.data.repository.VeiculoRepository
import com.mecaniq.app.data.repository.VeiculoRepositoryImpl
import com.mecaniq.app.ui.veiculo.VeiculoContract.Intent
import com.mecaniq.app.ui.veiculo.VeiculoContract.UiEffect
import com.mecaniq.app.ui.veiculo.VeiculoContract.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class VeiculoViewModel(
    private val repository: VeiculoRepository = VeiculoRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<UiEffect>()
    val uiEffect: SharedFlow<UiEffect> = _uiEffect.asSharedFlow()

    fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.OnPlacaChanged -> _uiState.update { it.copy(placaBusca = intent.placa, mensagemErro = null) }
            is Intent.BuscarVeiculo -> buscarVeiculoPorPlaca()
            is Intent.OnModeloChanged -> _uiState.update { it.copy(modeloInput = intent.modelo) }
            is Intent.OnMarcaChanged -> _uiState.update { it.copy(marcaInput = intent.marca) }
            is Intent.OnAnoChanged -> _uiState.update { it.copy(anoInput = intent.ano) }
            is Intent.OnKmChanged -> _uiState.update { it.copy(kmInput = intent.km) }
            is Intent.OnClienteIdChanged -> _uiState.update { it.copy(clienteIdInput = intent.clienteId) }
            is Intent.CadastrarVeiculo -> cadastrarNovoVeiculo()
            is Intent.LimparBusca -> _uiState.update { UiState() }
        }
    }

    private fun buscarVeiculoPorPlaca() {
        val placa = _uiState.value.placaBusca.trim()
        if (placa.length < 7) {
            _uiState.update { it.copy(mensagemErro = "A placa deve ter 7 caracteres.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, mensagemErro = null) }
            val result = repository.buscarPorPlaca(placa)

            result.onSuccess { veiculo ->
                if (veiculo != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            veiculoEncontrado = veiculo,
                            mostrarFormularioCadastro = false
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            veiculoEncontrado = null,
                            mostrarFormularioCadastro = true
                        )
                    }
                    _uiEffect.emit(UiEffect.MostrarToast("Veículo não encontrado. Preencha os dados para cadastrar."))
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        mensagemErro = "Erro de conexão: ${exception.message}"
                    )
                }
            }
        }
    }

    private fun cadastrarNovoVeiculo() {
        val currentState = _uiState.value
        val anoInt = currentState.anoInput.toIntOrNull() ?: 0
        val kmInt = currentState.kmInput.toIntOrNull() ?: 0

        if (currentState.modeloInput.isBlank() || currentState.marcaInput.isBlank()) {
            _uiState.update { it.copy(mensagemErro = "Preencha todos os campos obrigatórios.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSalvando = true, mensagemErro = null) }

            val request = CriarVeiculoRequest(
                clienteId = "4c1cedf1-b538-4c6d-b4c3-88132fe5dcbb",
                placa = currentState.placaBusca,
                modelo = currentState.modeloInput,
                marca = currentState.marcaInput,
                ano = anoInt,
                kmAtual = kmInt
            )

            val result = repository.cadastrarVeiculo(request)
            result.onSuccess { sucesso ->
                _uiState.update { it.copy(isSalvando = false) }
                if (sucesso) {
                    _uiEffect.emit(UiEffect.MostrarToast("Veículo cadastrado com sucesso!"))
                    buscarVeiculoPorPlaca() // Recarrega os dados do veículo recém-criado
                } else {
                    _uiState.update { it.copy(mensagemErro = "Erro ao salvar no servidor.") }
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(
                        isSalvando = false,
                        mensagemErro = "Falha ao cadastrar: ${exception.message}"
                    )
                }
            }
        }
    }
}