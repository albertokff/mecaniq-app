package com.mecaniq.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mecaniq.app.ui.alerta.AlertaScreen
import com.mecaniq.app.ui.dashboard.DashboardScreen
import com.mecaniq.app.ui.ordemservico.NovaOSScreen
import com.mecaniq.app.ui.theme.OficinaTheme
import com.mecaniq.app.ui.veiculo.VeiculoScreen

// Rotas da Aplicação
sealed interface Screen {
    data object Dashboard : Screen
    data class Veiculo(val placaInicial: String = "") : Screen
    data class NovaOS(val veiculoId: String = "") : Screen
    data class Alertas(val veiculoId: String = "") : Screen
}

@Composable
@Preview
fun App() {
    OficinaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // Gerenciador simples de estado de tela atual
            var currentScreen by remember { mutableStateOf<Screen>(Screen.Dashboard) }

            when (val screen = currentScreen) {
                is Screen.Dashboard -> {
                    DashboardScreen(
                        onNavegarParaNovaOS = {
                            currentScreen = Screen.NovaOS()
                        },
                        onNavegarParaVeiculo = { placa ->
                            currentScreen = Screen.Veiculo(placaInicial = placa)
                        }
                    )
                }

                is Screen.Veiculo -> {
                    VeiculoScreen(
                        onNavegarParaNovaOS = { veiculoId ->
                            currentScreen = Screen.NovaOS(veiculoId = veiculoId)
                        }
                    )
                }

                is Screen.NovaOS -> {
                    NovaOSScreen(
                        veiculoId = screen.veiculoId,
                        onVoltarOuConcluir = {
                            currentScreen = Screen.Dashboard
                        }
                    )
                }

                is Screen.Alertas -> {
                    AlertaScreen(
                        veiculoId = screen.veiculoId
                    )
                }
            }
        }
    }
}