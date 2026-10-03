package com.mecaniq.app.ui.alerta

import com.mecaniq.app.data.models.AlertaPreventivoDTO

object AlertaContract {

    // 1. Estados da UI
    data class UiState(
        val veiculoId: String = "",
        val isLoading: Boolean = false,
        val alertas: List<AlertaPreventivoDTO> = emptyList(),
        val mensagemErro: String? = null
    )

    // 2. Intenções do Utilizador (Intents)
    sealed interface Intent {
        data class CarregarAlertas(val veiculoId: String) : Intent
        data object Recarregar : Intent
    }

    // 3. Efeitos Colaterais (UiEffect)
    sealed interface UiEffect {
        data class MostrarToast(val mensagem: String) : UiEffect
    }
}