package com.mecaniq.app.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mecaniq.app.ui.components.PlacaTextField
import com.mecaniq.app.ui.dashboard.DashboardContract.Intent

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = androidx.lifecycle.viewmodel.compose.viewModel { DashboardViewModel() },
    onNavegarParaNovaOS: () -> Unit = {},
    onNavegarParaVeiculo: (String) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    var textoPlaca by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is DashboardContract.UiEffect.NavegarParaNovaOS -> onNavegarParaNovaOS()
                is DashboardContract.UiEffect.NavegarParaVeiculo -> onNavegarParaVeiculo(effect.placa)
                is DashboardContract.UiEffect.MostrarToast -> { /* Tratar via Snackbar/Toast */ }
            }
        }
    }

    Scaffold(
        topBar = {
            OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("MecaniQ - Painel da Oficina") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Cartão de Consulta Rápida por Placa
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Consulta Rápida de Veículo",
                        style = MaterialTheme.typography.titleMedium
                    )
                    PlacaTextField(
                        value = textoPlaca,
                        onValueChange = { textoPlaca = it }
                    )
                    Button(
                        onClick = { viewModel.handleIntent(Intent.BuscarPlaca(textoPlaca)) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Buscar Veículo / Histórico")
                    }
                }
            }

            // Indicadores Rápidos (Métricas)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CardMétrica(
                    titulo = "OS em Aberto",
                    valor = state.totalOsAbertas.toString(),
                    modifier = Modifier.weight(1f)
                )
                CardMétrica(
                    titulo = "Revisões Vencendo",
                    valor = state.alertasPendentes.toString(),
                    modifier = Modifier.weight(1f)
                )
            }

            // Botão de Ação Rápida: Abrir Nova OS
            Button(
                onClick = { viewModel.handleIntent(Intent.NovaOSClicada) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Emitir Nova Ordem de Serviço (OS)")
            }
        }
    }
}

@Composable
private fun CardMétrica(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = titulo, style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = valor, style = MaterialTheme.typography.headlineMedium)
        }
    }
}