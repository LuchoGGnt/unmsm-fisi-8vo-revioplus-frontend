@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.revioplus.app.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    onNavigateToDeposit: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ReVio+ · Inicio") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
                return@Column
            }

            state.errorMessage?.let { msg ->
                Text(text = msg)
                return@Column
            }

            Text(text = "Hola, ${state.userName} (${state.city})")
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Nivel ${state.levelNumber} · ${state.levelTitle}")
            Text(text = "XP actual: ${state.xpCurrent}/${state.xpNext}")
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Has reciclado ${state.bottles} botellas")
            Text(text = "CO₂ ahorrado: ${"%.2f".format(state.co2Kg)} kg")
            Spacer(modifier = Modifier.height(8.dp))

            if (state.challengeTitle.isNotBlank()) {
                Text(text = state.challengeTitle)
                Text(text = "Progreso: ${state.challengeProgress}/${state.challengeGoal} botellas")
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(text = "Actividad reciente:")
            state.recentDepositsText.forEach { line ->
                Text(text = "• $line")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToDeposit,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Ir a Depositar")
            }

            Button(
                onClick = onNavigateToProfile,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Ir a Perfil")
            }
        }
    }
}