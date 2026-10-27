package com.mecaniq.app.ui.splash

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mecaniq.app.data.local.SessionManager
import kotlinx.coroutines.delay

private val DarkBg = Color(0xFF090D16)
private val EmeraldPrimary = Color(0xFF10B981)

@Composable
fun SplashScreen(
    onNavigateToDashboard: () -> Unit,
    onNavigateToOnboarding: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        delay(1000)
        if (SessionManager.isUserLoggedIn()) {
            onNavigateToDashboard()
        } else {
            onNavigateToOnboarding()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(scale)
        ) {
            Text(
                text = "MecaniQ",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = EmeraldPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Gestão Inteligente para Oficinas",
                fontSize = 14.sp,
                color = Color(0xFF9CA3AF)
            )
        }
    }
}

@Composable
@Preview
fun SplasScreenPreview() {
    SplashScreen(
        onNavigateToDashboard = {},
        onNavigateToOnboarding = {}
    )
}