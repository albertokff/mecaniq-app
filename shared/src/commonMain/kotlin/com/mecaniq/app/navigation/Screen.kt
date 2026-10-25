package com.mecaniq.app.navigation

sealed interface Screen {
    data object Splash : Screen
    data object OnBoarding : Screen
    data object Login : Screen
    data object Dashboard : Screen
    data class Veiculo(val placaInicial: String = "") : Screen
    data class NovaOS(val veiculoId: String = "") : Screen
    data class Alertas(val veiculoId: String = "") : Screen
}
