@file:OptIn(ExperimentalMaterial3Api::class)

package com.revioplus.app.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect

// Colores del tema oscuro
private val DarkBackground = Color(0xFF121212)
private val DarkSurface = Color(0xFF1E1E1E)
private val TextWhite = Color.White
private val TextGray = Color.Gray
private val AccentColor = Color(0xFF4CAF50) // Un verde para acentos

@Composable
fun HomeScreen(
    onNavigateToDeposit: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LifecycleResumeEffect(Unit) {
        viewModel.loadDashboard()
        onPauseOrDispose { }
    }

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "ReVio+ · Inicio", 
                        fontWeight = FontWeight.Bold, 
                        color = TextWhite
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
            )
        }
    ) { innerPadding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AccentColor)
            }
        } else if (state.errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.errorMessage!!, color = Color.Red)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. Tarjeta de Saludo y Nivel
                WelcomeCard(state)

                // 2. Tarjeta de Desafío
                if (state.challengeTitle.isNotBlank() && state.challengeTitle != "Sin desafío activo") {
                    ChallengeCard(state)
                }
                
                // 3. Tarjeta de Actividad Reciente
                RecentActivityCard(state)

                // Botones de acción (opcional, se pueden mover a la BottomBar)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = onNavigateToDeposit, modifier = Modifier.weight(1f)) { Text("Depositar") }
                    Button(onClick = onNavigateToProfile, modifier = Modifier.weight(1f)) { Text("Ver Perfil") }
                }
            }
        }
    }
}

@Composable
private fun WelcomeCard(state: HomeUiState) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Hola, ${state.userName}", style = MaterialTheme.typography.headlineSmall, color = TextWhite)
            Text(state.city, style = MaterialTheme.typography.bodyMedium, color = TextGray)
            Spacer(Modifier.height(16.dp))
            Text("Nivel ${state.levelNumber}: ${state.levelTitle}", style = MaterialTheme.typography.titleMedium, color = TextWhite, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            val progress = if (state.xpNext > 0) state.xpCurrent.toFloat() / state.xpNext.toFloat() else 0f
            LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)), color = AccentColor, trackColor = Color.DarkGray)
            Text("${state.xpCurrent} / ${state.xpNext} XP", style = MaterialTheme.typography.bodySmall, color = TextGray, modifier = Modifier.align(Alignment.End))
        }
    }
}

@Composable
private fun ChallengeCard(state: HomeUiState) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Desafío Semanal", style = MaterialTheme.typography.titleLarge, color = TextWhite, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(state.challengeTitle, style = MaterialTheme.typography.bodyLarge, color = TextWhite)
            Spacer(Modifier.height(8.dp))
            val progress = if (state.challengeGoal > 0) state.challengeProgress.toFloat() / state.challengeGoal.toFloat() else 0f
            LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)), color = AccentColor, trackColor = Color.DarkGray)
            Text("${state.challengeProgress} / ${state.challengeGoal} botellas", style = MaterialTheme.typography.bodySmall, color = TextGray, modifier = Modifier.align(Alignment.End))
        }
    }
}

@Composable
private fun RecentActivityCard(state: HomeUiState) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Actividad Reciente", style = MaterialTheme.typography.titleLarge, color = TextWhite, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            if (state.recentDepositsText.isEmpty()) {
                Text("Aún no tienes depósitos.", color = TextGray, modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                state.recentDepositsText.forEach {
                    Text("• $it", style = MaterialTheme.typography.bodyMedium, color = TextGray)
                    Spacer(Modifier.height(4.dp))
                }
            }
        }
    }
}
