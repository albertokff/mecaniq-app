package com.mecaniq.app.ui.veiculo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mecaniq.app.ui.components.PlacaTextField
import com.mecaniq.app.ui.veiculo.VeiculoContract.Intent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VeiculoScreen(
    viewModel: VeiculoViewModel = androidx.lifecycle.viewmodel.compose.viewModel { VeiculoViewModel() },
    onNavegarParaNovaOS: (String) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is VeiculoContract.UiEffect.MostrarToast -> { /* Tratar via Snackbar */ }
                is VeiculoContract.UiEffect.NavegarParaNovaOS -> onNavegarParaNovaOS(effect.veiculoId)
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Consulta e Cadastro de Veículo") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Bloco de Busca por Placa
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PlacaTextField(
                    value = state.placaBusca,
                    onValueChange = { viewModel.handleIntent(Intent.OnPlacaChanged(it)) },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = { viewModel.handleIntent(Intent.BuscarVeiculo) },
                    enabled = !state.isLoading
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text("Buscar")
                    }
                }
            }

            state.mensagemErro?.let { erro ->
                Text(text = erro, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
            }

            // Exibição dos dados do Veículo Encontrado
            state.veiculoEncontrado?.let { veiculo ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Veículo Localizado", style = MaterialTheme.typography.titleMedium)
                        Text("Marca / Modelo: ${veiculo.marca} ${veiculo.modelo}")
                        Text("Ano: ${veiculo.ano}")
                        Text("Quilometragem Atual: ${veiculo.kmAtual} km")

                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { viewModel.handleIntent(Intent.OnPlacaChanged(veiculo.placa)) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Abrir Nova Ordem de Serviço para este Veículo")
                        }
                    }
                }
            }

            // Formulário de Cadastro caso a placa não exista
            if (state.mostrarFormularioCadastro) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Veículo não encontrado. Cadastre abaixo:", style = MaterialTheme.typography.titleMedium)

                        OutlinedTextField(
                            value = state.marcaInput,
                            onValueChange = { viewModel.handleIntent(Intent.OnMarcaChanged(it)) },
                            label = { Text("Marca (ex: Volkswagen)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = state.modeloInput,
                            onValueChange = { viewModel.handleIntent(Intent.OnModeloChanged(it)) },
                            label = { Text("Modelo (ex: Gol 1.0)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = state.anoInput,
                                onValueChange = { viewModel.handleIntent(Intent.OnAnoChanged(it)) },
                                label = { Text("Ano") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = state.kmInput,
                                onValueChange = { viewModel.handleIntent(Intent.OnKmChanged(it)) },
                                label = { Text("KM Atual") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Button(
                            onClick = { viewModel.handleIntent(Intent.CadastrarVeiculo) },
                            enabled = !state.isSalvando,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (state.isSalvando) {
                                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary)
                            } else {
                                Text("Salvar Veículo")
                            }
                        }
                    }
                }
            }
        }
    }
}