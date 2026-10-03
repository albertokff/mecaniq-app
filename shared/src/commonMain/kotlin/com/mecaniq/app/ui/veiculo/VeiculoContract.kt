package com.mecaniq.app.ui.veiculo

import com.mecaniq.app.data.models.VeiculoDTO

object VeiculoContract {

    // 1. Estados da UI
    data class UiState(
        val placaBusca: String = "",
        val isLoading: Boolean = false,
        val veiculoEncontrado: VeiculoDTO? = null,
        val mostrarFormularioCadastro: Boolean = false,

        // Campos para o formulário de cadastro
        val clienteIdInput: String = "",
        val modeloInput: String = "",
        val marcaInput: String = "",
        val anoInput: String = "",
        val kmInput: String = "",

        val isSalvando: Boolean = false,
        val mensagemErro: String? = null
    )

    // 2. Intenções do Utilizador (Intents)
    sealed interface Intent {
        data class OnPlacaChanged(val placa: String) : Intent
        data object BuscarVeiculo : Intent
        data class OnModeloChanged(val modelo: String) : Intent
        data class OnMarcaChanged(val marca: String) : Intent
        data class OnAnoChanged(val ano: String) : Intent
        data class OnKmChanged(val km: String) : Intent
        data class OnClienteIdChanged(val clienteId: String) : Intent
        data object CadastrarVeiculo : Intent
        data object LimparBusca : Intent
    }

    // 3. Efeitos Colaterais (UiEffect)
    sealed interface UiEffect {
        data class MostrarToast(val mensagem: String) : UiEffect
        data class NavegarParaNovaOS(val veiculoId: String) : UiEffect
    }
}