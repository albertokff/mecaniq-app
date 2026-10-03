package com.mecaniq.app.ui.alerta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mecaniq.app.data.models.AlertaPreventivoDTO
import com.mecaniq.app.ui.alerta.AlertaContract.Intent
import com.mecaniq.app.ui.theme.StatusDanger
import com.mecaniq.app.ui.theme.StatusSuccess
import com.mecaniq.app.ui.theme.StatusWarning

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertaScreen(
    veiculoId: String = "",
    viewModel: AlertaViewModel = androidx.lifecycle.viewmodel.compose.viewModel { AlertaViewModel(veiculoIdInicial = veiculoId) }
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Alertas de Manutenção Preventiva") }) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.mensagemErro != null) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.mensagemErro!!, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.handleIntent(Intent.Recarregar) }) {
                        Text("Tentar Novamente")
                    }
                }
            } else if (state.alertas.isEmpty()) {
                Text(
                    text = "Nenhum alerta pendente para este veículo.",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.alertas) { alerta ->
                        ItemCardAlerta(alerta = alerta)
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemCardAlerta(alerta: AlertaPreventivoDTO) {
    // Define a cor e o texto do badge com base no status do alerta
    val (corBadge, textoBadge) = when (alerta.statusAlerta.uppercase()) {
        "VENCIDO", "CRITICO" -> StatusDanger to "VENCIDO"
        "PROXIMO", "ATENCAO" -> StatusWarning to "PRÓXIMO"
        else -> StatusSuccess to "EM DIA"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = alerta.descricao,
                    style = MaterialTheme.typography.titleMedium
                )

                // Badge Visual de Status
                Box(
                    modifier = Modifier
                        .background(color = corBadge, shape = RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = textoBadge,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Última troca: ${alerta.kmUltimaTroca} km", style = MaterialTheme.typography.bodySmall)
                Text("Próxima troca: ${alerta.kmProximaTroca} km", style = MaterialTheme.typography.bodySmall)
            }

            Text(
                text = "Faltam ${alerta.kmRestantes} km (Previsão: ${alerta.dataProximaTrocaEstimada})",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}