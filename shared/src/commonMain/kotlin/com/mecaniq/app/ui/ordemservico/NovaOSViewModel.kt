package com.mecaniq.app.ui.ordemservico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mecaniq.app.data.models.ItemOSDTO
import com.mecaniq.app.data.models.OrdemServicoDTO
import com.mecaniq.app.data.repository.OrdemServicoRepository
import com.mecaniq.app.data.repository.OrdemServicoRepositoryImpl
import com.mecaniq.app.ui.ordemservico.NovaOSContract.Intent
import com.mecaniq.app.ui.ordemservico.NovaOSContract.UiEffect
import com.mecaniq.app.ui.ordemservico.NovaOSContract.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.Clock

class NovaOSViewModel(
    private val veiculoIdInicial: String = "",
    private val repository: OrdemServicoRepository = OrdemServicoRepositoryImpl()
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState(veiculoId = veiculoIdInicial))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<UiEffect>()
    val uiEffect: SharedFlow<UiEffect> = _uiEffect.asSharedFlow()

    fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.OnKmEntradaChanged -> _uiState.update { it.copy(kmEntradaInput = intent.km) }
            is Intent.OnObservacoesChanged -> _uiState.update { it.copy(observacoesInput = intent.obs) }
            is Intent.OnNovoItemDescricaoChanged -> _uiState.update { it.copy(novoItemDescricao = intent.desc) }
            is Intent.OnNovoItemTipoChanged -> _uiState.update { it.copy(novoItemTipo = intent.tipo) }
            is Intent.OnNovoItemQuantidadeChanged -> _uiState.update { it.copy(novoItemQuantidade = intent.qtd) }
            is Intent.OnNovoItemPrecoChanged -> _uiState.update { it.copy(novoItemPrecoUnitario = intent.preco) }
            is Intent.AdicionarItem -> adicionarItemLista()
            is Intent.RemoverItem -> removerItemLista(intent.itemId)
            is Intent.EmitirOrdemServico -> emitirOS()
        }
    }

    private fun adicionarItemLista() {
        val state = _uiState.value
        val desc = state.novoItemDescricao.trim()
        val qtd = state.novoItemQuantidade.toIntOrNull() ?: 0
        val preco = state.novoItemPrecoUnitario.toDoubleOrNull() ?: 0.0

        if (desc.isBlank() || qtd <= 0 || preco <= 0.0) {
            _uiState.update { it.copy(mensagemErro = "Preencha os dados do item corretamente.") }
            return
        }

        val novoItem = ItemOSDTO(
            id = "item_${Clock.System.now().toEpochMilliseconds()}",
            descricao = desc,
            tipo = state.novoItemTipo,
            quantidade = qtd,
            precoUnitario = preco
        )

        _uiState.update {
            it.copy(
                itens = it.itens + novoItem,
                novoItemDescricao = "",
                novoItemQuantidade = "1",
                novoItemPrecoUnitario = "",
                mensagemErro = null
            )
        }
    }

    private fun removerItemLista(itemId: String) {
        _uiState.update { state ->
            state.copy(itens = state.itens.filterNot { it.id == itemId })
        }
    }

    private fun emitirOS() {
        val state = _uiState.value
        val kmInt = state.kmEntradaInput.toIntOrNull() ?: 0

        if (state.itens.isEmpty()) {
            _uiState.update { it.copy(mensagemErro = "Adicione ao menos um item ou serviço na OS.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isEmitting = true, mensagemErro = null) }

            val osDTO = OrdemServicoDTO(
                id = "", // O backend gera o ID
                oficinaId = state.oficinaId,
                veiculoId = state.veiculoId.ifBlank { "veiculo-default-id" },
                status = "ABERTA",
                kmEntrada = kmInt,
                valorTotal = state.valorTotal,
                observacoes = state.observacoesInput.ifBlank { null },
                itens = state.itens
            )

            val result = repository.criarOS(osDTO)
            result.onSuccess {
                _uiState.update { state -> state.copy(isEmitting = false) }
                _uiEffect.emit(UiEffect.MostrarToast("Ordem de Serviço emitida com sucesso!"))
                _uiEffect.emit(UiEffect.OSEmitidaComSucesso)
            }.onFailure { exception ->
                _uiState.update { state ->
                    state.copy(isEmitting = false, mensagemErro = "Falha ao emitir OS: ${exception.message}")
                }
            }
        }
    }
}