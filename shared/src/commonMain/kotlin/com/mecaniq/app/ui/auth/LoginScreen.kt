package com.mecaniq.app.ui.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mecaniq.app.data.models.AuthResponse
import com.mecaniq.app.data.models.LoginRequest
import com.mecaniq.app.data.models.RegisterOficinaRequest
import com.mecaniq.app.data.repository.AuthRepository
import com.mecaniq.app.domain.service.AuthService

private val DarkBg: Color = Color(0xFF090D16)
private val SurfaceCard = Color(0xFF111827)
private val EmeraldPrimary = Color(0xFF10B981)
private val TextWhite = Color(0xFFF9FAFB)
private val TextMuted = Color(0xFF9CA3AF)

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.sessionSuccess) {
        if (uiState.sessionSuccess != null) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceCard)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (uiState.isRegisterMode) "Cadastrar Oficina" else "MecaniQ",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = EmeraldPrimary
                    )

                    Text(
                        text = if (uiState.isRegisterMode) "Crie sua conta" else "Faça seu login",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    uiState.errorMessage?.let { erro ->
                        Text(
                            text = erro,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }

                    AnimatedVisibility(visible = uiState.isRegisterMode) {
                        Column {
                            OutlinedTextField(
                                value = uiState.nomeOficina,
                                onValueChange = { viewModel.processIntent(LoginUiIntent.OnNomeOficinaChanged(it)) },
                                label = { Text("Nome da Oficina") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = uiState.nomeAdmin,
                                onValueChange = { viewModel.processIntent(LoginUiIntent.OnNomeAdminChanged(it)) },
                                label = { Text("Seu Nome") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = uiState.telefone,
                                onValueChange = { viewModel.processIntent(LoginUiIntent.OnTelefoneChanged(it)) },
                                label = { Text("Telefone / Whatsapp") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                singleLine = true
                            )
                        }
                    }

                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = { viewModel.processIntent(LoginUiIntent.OnEmailChanged(it)) },
                        label = { Text("E-mail") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = uiState.senha,
                        onValueChange = { viewModel.processIntent(LoginUiIntent.OnSenhaChanged(it)) },
                        label = { Text("Senha") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )

                    Button(
                        onClick = { viewModel.processIntent(LoginUiIntent.Submit) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                        enabled = !uiState.isLoading
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text(
                                text = if (uiState.isRegisterMode) "Cadastrar Oficinal" else "Entrar",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }

                    TextButton(
                        onClick = { viewModel.processIntent(LoginUiIntent.ToggleRegisterMode) },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            text = if (uiState.isRegisterMode) "Já tem uma conta? Faça Login" else "Não tem conta? Cadastre sua Oficina",
                            color = Color.LightGray,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    // Cria uma versão fake/mock do AuthService e ViewModel para visualização
    val fakeRepository = FakeAuthRepository()
    val fakeApiService = AuthService(fakeRepository)
    val fakeService = AuthService(fakeRepository)
    val fakeViewModel = LoginViewModel(fakeService)

    MaterialTheme {
        LoginScreen(
            viewModel = fakeViewModel,
            onLoginSuccess = {}
        )
    }
}

// Repositório Fake apenas para carregar o Compose Preview sem dependências de rede
class FakeAuthRepository : AuthRepository {
    override suspend fun login(request: LoginRequest): Result<AuthResponse> {
        return Result.success(
            AuthResponse(
                token = "fake-jwt-token",
                usuarioId = "123",
                nome = "Oficina Teste",
                email = request.email,
                role = "OFICINA_ADMIN",
                oficinaId = "456"
            )
        )
    }

    override suspend fun registerOficina(request: RegisterOficinaRequest): Result<AuthResponse> {
        return Result.success(
            AuthResponse(
                token = "fake-jwt-token",
                usuarioId = "123",
                nome = request.nomeAdmin,
                email = request.emailAdmin,
                role = "OFICINA_ADMIN",
                oficinaId = "456"
            )
        )
    }
}