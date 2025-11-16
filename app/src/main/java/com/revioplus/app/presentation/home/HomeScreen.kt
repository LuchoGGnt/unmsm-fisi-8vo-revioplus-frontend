@file:OptIn(ExperimentalMaterial3Api::class)

package com.revioplus.app.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNavigateToDeposit: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ReVio+ · Inicio") }
            )
        }
    ) { innerPaddin ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddin)
                .padding(16.dp)
        ) {
            Text("Aquí irá el panel principal: progreso, desafío semanal, actividad reciente.")

            Button(
                onClick = onNavigateToDeposit,
                modifier = Modifier.padding(top = 16.dp)
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