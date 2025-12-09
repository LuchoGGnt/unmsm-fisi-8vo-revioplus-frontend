@file:OptIn(ExperimentalMaterial3Api::class)

package com.revioplus.app.presentation.profile.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.revioplus.app.presentation.profile.ProfileTabs

// Colores del tema oscuro
private val DarkBackground = Color(0xFF121212)
private val DarkSurface = Color(0xFF1E1E1E)
private val TextWhite = Color.White
private val TextGray = Color.Gray
private val AccentColor = Color(0xFF4CAF50)

@Composable
fun ProfileHistoryScreen(
    onNavigateBackToProfile: () -> Unit,
    onNavigateToWallet: () -> Unit,
    viewModel: ProfileHistoryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LifecycleResumeEffect(Unit) {
        viewModel.loadHistory()
        onPauseOrDispose { }
    }

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = { Text("Historial", fontWeight = FontWeight.Bold, color = TextWhite) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBackToProfile) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Volver", tint = TextWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBackground)
            )
        }
    ) { innerPadding: PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ProfileTabs(
                selectedIndex = 2,
                modifier = Modifier.fillMaxWidth(),
                onProfileClick = onNavigateBackToProfile,
                onWalletClick = onNavigateToWallet,
                onHistoryClick = { }
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AccentColor)
                }
            } else if (uiState.errorMessage != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = uiState.errorMessage!!, color = Color.Red)
                }
            } else if (uiState.recentDeposits.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tienes depósitos registrados.", color = TextGray)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(uiState.recentDeposits) { item ->
                        DepositHistoryRow(item)
                    }
                }
            }
        }
    }
}

@Composable
private fun DepositHistoryRow(item: DepositHistoryItemUi) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = DarkSurface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("${item.bottles} botellas", style = MaterialTheme.typography.titleMedium, color = TextWhite, fontWeight = FontWeight.Bold)
                Text(item.location, style = MaterialTheme.typography.bodySmall, color = TextGray)
                Text(item.dateText, style = MaterialTheme.typography.bodySmall, color = TextGray)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(item.xpEarnedText, style = MaterialTheme.typography.bodyMedium, color = AccentColor, fontWeight = FontWeight.Bold)
                Text(item.co2SavedText, style = MaterialTheme.typography.bodySmall, color = TextGray)
            }
        }
    }
}
