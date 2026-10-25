package com.mecaniq.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mecaniq.app.di.AppModule
import com.mecaniq.app.navigation.Screen
import com.mecaniq.app.ui.alerta.AlertaScreen
import com.mecaniq.app.ui.auth.LoginScreen
import com.mecaniq.app.ui.dashboard.DashboardScreen
import com.mecaniq.app.ui.onboarding.OnboardingScreen
import com.mecaniq.app.ui.ordemservico.NovaOSScreen
import com.mecaniq.app.ui.splash.SplashScreen
import com.mecaniq.app.ui.theme.OficinaTheme
import com.mecaniq.app.ui.veiculo.VeiculoScreen

@Composable
@Preview
fun App() {
    OficinaTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // Gerenciador de estado de navegação atual
            var currentScreen by remember { mutableStateOf<Screen>(Screen.Splash) }

            when (val screen = currentScreen) {
                is Screen.Splash -> {
                    SplashScreen(
                        onNavigateToDashboard = {
                            currentScreen = Screen.Dashboard
                        },
                        onNavigateToOnboarding = {
                            currentScreen = Screen.OnBoarding
                        }
                    )
                }

                is Screen.OnBoarding -> {
                    OnboardingScreen(
                        onFinishOnboarding = {
                            currentScreen = Screen.Login
                        }
                    )
                }

                is Screen.Login -> {
                    LoginScreen(
                        viewModel = remember { AppModule.provideLoginViewModel() },
                        onLoginSuccess = {
                            currentScreen = Screen.Dashboard
                        }
                    )
                }

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

                else -> {}
            }
        }
    }
}
