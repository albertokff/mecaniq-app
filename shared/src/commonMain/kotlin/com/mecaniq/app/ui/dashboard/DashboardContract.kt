package com.mecaniq.app.ui.dashboard

import com.mecaniq.app.data.models.OrdemServicoDTO

object DashboardContract {
    data class UiState(
        val isLoading: Boolean = false,
        val totalOsAbertas: Int = 0,
        val alertasPendentes: Int = 0,
        val ultimasOrdensServico: List<OrdemServicoDTO> = emptyList(),
        val errorMessage: String? = null
    )

    sealed interface Intent {
        data object CarregarDados : Intent
        data class BuscarPlaca(val placa: String) : Intent
        data object NovaOSClicada: Intent
    }

    sealed interface UiEffect {
        data class NavegarParaNovaOS(val placa: String? = null) : UiEffect
        data class NavegarParaVeiculo(val placa: String) : UiEffect
        data class MostrarToast(val mensagem: String) : UiEffect
    }
}