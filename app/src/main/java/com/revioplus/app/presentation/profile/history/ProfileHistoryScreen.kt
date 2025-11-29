@file:OptIn(ExperimentalMaterial3Api::class)

package com.revioplus.app.presentation.profile.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun ProfileHistoryScreen(
    onNavigateBackToProfile: () -> Unit,
    onNavigateToWallet: () -> Unit,
    viewModel: ProfileHistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBackToProfile) {
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
                .padding(16.dp)
        ) {
            // 🔹 Pestañas: Historial seleccionado (índice 2)
            ProfileTabs(
                selectedIndex = 2,
                modifier = Modifier.fillMaxWidth(),
                onProfileClick = onNavigateBackToProfile,
                onWalletClick = onNavigateToWallet,
                onHistoryClick = { /* ya estamos en Historial */ }
            )

            Spacer(modifier = Modifier.height(16.dp))

            when {
                uiState.isLoading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.errorMessage != null -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = uiState.errorMessage ?: "Error desconocido")
                    }
                }

                else -> {
                    // ------- Depósitos recientes -------
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Depósitos recientes",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            uiState.recentDeposits.forEach { item ->
                                DepositHistoryRow(item)
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }

                    // ------- Resumen del mes -------
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Resumen del mes",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        text = uiState.totalDepositsThisMonthText,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Text(
                                        text = "Depósitos realizados",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                Column(
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Text(
                                        text = uiState.totalBottlesThisMonthText,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Text(
                                        text = "Botellas recicladas",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DepositHistoryRow(item: DepositHistoryItemUi) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "${item.bottles} botellas",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = item.location,
            style = MaterialTheme.typography.bodySmall
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.dateText,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "${item.xpEarnedText}  ·  ${item.co2SavedText}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
