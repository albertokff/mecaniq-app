package com.mecaniq.app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization

@Composable
fun PlacaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Mantém apenas letras/números em caixa alta e limita a 7 caracteres (Padrão Mercosul/Antigo)
            if (newValue.length <= 7) {
                onValueChange(newValue.uppercase())
            }
        },
        label = { Text("Placa do Veículo") },
        placeholder = { Text("ABC1D23") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Characters
        ),
        modifier = modifier.fillMaxWidth()
    )
}