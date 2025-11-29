@file:OptIn(ExperimentalMaterial3Api::class)

package com.revioplus.app.presentation.profile.wallet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.revioplus.app.presentation.profile.ProfileTabs

@Composable
fun WalletScreen(
    onNavigateBack: () -> Unit,          // ← vuelve a Perfil
    onNavigateToHistory: () -> Unit = {},// ← para cuando implementemos Historial
    viewModel: WalletViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Billetera") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding: PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            ProfileTabs(
                selectedIndex = 1,
                modifier = Modifier.fillMaxWidth(),
                onProfileClick = onNavigateBack,
                onWalletClick = { /* ya estamos en billetera */ },
                onHistoryClick = onNavigateToHistory
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                uiState.isLoading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.errorMessage != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = uiState.errorMessage ?: "Error desconocido")
                    }
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Saldo disponible",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = uiState.saldoDisponibleText,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Saldo bloqueado: ${uiState.saldoBloqueadoText}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Límites y retiros",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Límite diario de retiros: ${uiState.limiteRetiroDiarioText}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Mínimo por retiro: ${uiState.montoMinimoRetiroText}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Estado de la billetera",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Estado: ${uiState.estadoText}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Último movimiento: ${uiState.ultimaActualizacionText}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
