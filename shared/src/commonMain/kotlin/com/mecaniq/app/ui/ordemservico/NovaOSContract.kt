package com.mecaniq.app.ui.ordemservico

import com.mecaniq.app.data.models.ItemOSDTO

object NovaOSContract {

    // 1. Estados da UI
    data class UiState(
        val veiculoId: String = "",
        val oficinaId: String = "oficina-default-id",
        val kmEntradaInput: String = "",
        val observacoesInput: String = "",
        val itens: List<ItemOSDTO> = emptyList(),

        // Campos temporários para adicionar um novo item
        val novoItemDescricao: String = "",
        val novoItemTipo: String = "PEÇA", // PEÇA ou SERVIÇO
        val novoItemQuantidade: String = "1",
        val novoItemPrecoUnitario: String = "",

        val isEmitting: Boolean = false,
        val mensagemErro: String? = null
    ) {
        val valorTotal: Double
            get() = itens.sumOf { it.quantidade * it.precoUnitario }
    }

    // 2. Intenções do Utilizador (Intents)
    sealed interface Intent {
        data class OnKmEntradaChanged(val km: String) : Intent
        data class OnObservacoesChanged(val obs: String) : Intent
        data class OnNovoItemDescricaoChanged(val desc: String) : Intent
        data class OnNovoItemTipoChanged(val tipo: String) : Intent
        data class OnNovoItemQuantidadeChanged(val qtd: String) : Intent
        data class OnNovoItemPrecoChanged(val preco: String) : Intent
        data object AdicionarItem : Intent
        data class RemoverItem(val itemId: String) : Intent
        data object EmitirOrdemServico : Intent
    }

    // 3. Efeitos Colaterais (UiEffect)
    sealed interface UiEffect {
        data class MostrarToast(val mensagem: String) : UiEffect
        data object OSEmitidaComSucesso : UiEffect
    }
}