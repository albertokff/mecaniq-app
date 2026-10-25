package com.mecaniq.app.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkBg = Color(0xFF090D16)
private val SurfaceCard = Color(0xFF111827)
private val EmeraldPrimary = Color(0xFF10B981)
private val TextWhite = Color(0xFFF9FAFB)
private val TextMuted = Color(0xFF9CA3AF)

data class OnboardingStep(
    val title: String,
    val description: String,
    val icon: ImageVector
)

@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit
) {
    val steps = listOf(
        OnboardingStep(
            title = "Ordens de Serviço Agilizadas",
            description = "Crie e gerencie Ordens de Serviço (OS) com peças, serviços e cálculo de totais em poucos cliques.",
            icon = Icons.AutoMirrored.Outlined.Assignment
        ),
        OnboardingStep(
            title = "Envio Direto pelo WhatsApp",
            description = "Mande orçamentos e históricos de manutenção direto no WhatsApp do cliente via link Web sem complicações.",
            icon = Icons.AutoMirrored.Outlined.Chat
        ),
        OnboardingStep(
            title = "Alertas Preventivos & Retenção",
            description = "Fidelize seus clientes com alertas automáticos de troca de óleo, correias e revisões por KM.",
            icon = Icons.Outlined.NotificationsActive
        )
    )

    var currentStepIndex by remember { mutableStateOf(0) }
    val currentStep = steps[currentStepIndex]

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = SurfaceCard)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Renderização correta do Vetor usando Icon
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(EmeraldPrimary.copy(alpha = 0.15f), RoundedCornerShape(20.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = currentStep.icon,
                        contentDescription = null,
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = currentStep.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = currentStep.description,
                    fontSize = 14.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 28.dp)
                ) {
                    steps.indices.forEach { index ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(if (index == currentStepIndex) 12.dp else 8.dp)
                                .background(
                                    color = if (index == currentStepIndex) EmeraldPrimary else TextMuted,
                                    shape = CircleShape
                                )
                        )
                    }
                }

                Button(
                    onClick = {
                        if (currentStepIndex < steps.size - 1) {
                            currentStepIndex++
                        } else {
                            onFinishOnboarding()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary)
                ) {
                    Text(
                        text = if (currentStepIndex < steps.size - 1) "Próximo" else "Começar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                if (currentStepIndex < steps.size - 1) {
                    TextButton(
                        onClick = onFinishOnboarding,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            text = "Pular",
                            color = TextMuted
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun OnboardingScreenPreview() {
    OnboardingScreen(
        onFinishOnboarding = {}
    )
}
