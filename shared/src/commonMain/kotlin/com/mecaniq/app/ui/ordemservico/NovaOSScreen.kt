package com.mecaniq.app.ui.ordemservico

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mecaniq.app.ui.ordemservico.NovaOSContract.Intent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovaOSScreen(
    veiculoId: String = "",
    viewModel: NovaOSViewModel = androidx.lifecycle.viewmodel.compose.viewModel { NovaOSViewModel(veiculoIdInicial = veiculoId) },
    onVoltarOuConcluir: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is NovaOSContract.UiEffect.MostrarToast -> { /* Tratar via Snackbar/Toast */ }
                is NovaOSContract.UiEffect.OSEmitidaComSucesso -> onVoltarOuConcluir()
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Nova Ordem de Serviço") }) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Seção de Informações Básicas
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Dados de Entrada", style = MaterialTheme.typography.titleMedium)

                        OutlinedTextField(
                            value = state.kmEntradaInput,
                            onValueChange = { viewModel.handleIntent(Intent.OnKmEntradaChanged(it)) },
                            label = { Text("KM de Entrada") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = state.observacoesInput,
                            onValueChange = { viewModel.handleIntent(Intent.OnObservacoesChanged(it)) },
                            label = { Text("Observações (opcional)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Seção para Inclusão de Novo Item/Peça
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Adicionar Peça ou Serviço", style = MaterialTheme.typography.titleMedium)

                        OutlinedTextField(
                            value = state.novoItemDescricao,
                            onValueChange = { viewModel.handleIntent(Intent.OnNovoItemDescricaoChanged(it)) },
                            label = { Text("Descrição (ex: Óleo 5W30, Pastilha de Freio)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = state.novoItemQuantidade,
                                onValueChange = { viewModel.handleIntent(Intent.OnNovoItemQuantidadeChanged(it)) },
                                label = { Text("Qtd") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = state.novoItemPrecoUnitario,
                                onValueChange = { viewModel.handleIntent(Intent.OnNovoItemPrecoChanged(it)) },
                                label = { Text("Valor Unit. (R$)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.weight(1.5f)
                            )
                        }

                        Button(
                            onClick = { viewModel.handleIntent(Intent.AdicionarItem) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Incluir na OS")
                        }
                    }
                }
            }

            state.mensagemErro?.let { erro ->
                item {
                    Text(text = erro, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
                }
            }

            // Lista de Itens Adicionados
            item {
                Text("Itens da OS (${state.itens.size})", style = MaterialTheme.typography.titleMedium)
            }

            items(state.itens, key = { it.id }) { itemOS ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(itemOS.descricao, style = MaterialTheme.typography.bodyLarge)
                            Text(
                                "${itemOS.quantidade}x R$ ${itemOS.precoUnitario} = R$ ${itemOS.quantidade * itemOS.precoUnitario}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        IconButton(onClick = { viewModel.handleIntent(Intent.RemoverItem(itemOS.id)) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remover Item", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }

            // Resumo e Botão de Emissão
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Valor Total:", style = MaterialTheme.typography.titleMedium)
                            Text("R$ ${state.valorTotal}", style = MaterialTheme.typography.titleLarge)
                        }

                        Button(
                            onClick = { viewModel.handleIntent(Intent.EmitirOrdemServico) },
                            enabled = !state.isEmitting,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (state.isEmitting) {
                                CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary)
                            } else {
                                Text("Finalizar e Emitir OS")
                            }
                        }
                    }
                }
            }
        }
    }
}