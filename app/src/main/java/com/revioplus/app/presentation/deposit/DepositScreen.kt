@file:OptIn(ExperimentalMaterial3Api::class)

package com.revioplus.app.presentation.deposit

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

// Colores del tema oscuro
private val DarkBackground = Color(0xFF121212)
private val DarkSurface = Color(0xFF1E1E1E)
private val TextWhite = Color.White
private val TextGray = Color.Gray
private val AccentColor = Color(0xFF4CAF50)

@Composable
fun DepositScreen(
    onNavigateBack: () -> Unit,
    viewModel: DepositViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    // Navegar atrás automáticamente al registrar con éxito
    LaunchedEffect(state.successMessage) {
        if (state.successMessage != null) {
            kotlinx.coroutines.delay(2000) // Esperar 2 segundos para que el usuario lea el mensaje
            onNavigateBack()
        }
    }

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = { Text("Registrar Depósito", color = TextWhite, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver", tint = TextWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Si se está guardando o ya se guardó, mostrar mensaje, si no, el contador
            if (state.isSaving || state.successMessage != null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (state.isSaving) {
                        CircularProgressIndicator(color = AccentColor)
                        Spacer(Modifier.height(16.dp))
                        Text("Registrando...", color = TextWhite, fontSize = 18.sp)
                    } else {
                        Text("¡Éxito!", style = MaterialTheme.typography.headlineMedium, color = AccentColor)
                        Spacer(Modifier.height(8.dp))
                        Text(state.successMessage!!, color = TextGray, textAlign = TextAlign.Center)
                    }
                }
            } else {
                Text("¿Cuántas botellas reciclaste?", style = MaterialTheme.typography.headlineSmall, color = TextWhite, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(onClick = viewModel::onDecrement, modifier = Modifier.size(48.dp), colors = IconButtonDefaults.iconButtonColors(containerColor = DarkSurface, contentColor = TextWhite)) {
                        Icon(Icons.Default.Remove, contentDescription = "Restar")
                    }

                    Text(text = state.cantidadBotellas.toString(), style = MaterialTheme.typography.displayLarge, color = TextWhite, fontWeight = FontWeight.Bold)

                    IconButton(onClick = viewModel::onIncrement, modifier = Modifier.size(48.dp), colors = IconButtonDefaults.iconButtonColors(containerColor = DarkSurface, contentColor = TextWhite)) {
                        Icon(Icons.Default.Add, contentDescription = "Sumar")
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = viewModel::onRegisterDeposit,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentColor)
                ) {
                    Text("Confirmar Depósito", fontSize = 16.sp)
                }

                state.errorMessage?.let {
                    Spacer(Modifier.height(16.dp))
                    Text(it, color = Color.Red, textAlign = TextAlign.Center)
                }
            }
        }
    }
}
