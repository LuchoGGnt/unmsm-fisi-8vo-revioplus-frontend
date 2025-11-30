@file:OptIn(ExperimentalMaterial3Api::class)

package com.revioplus.app.presentation.deposit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DepositScreen(
    onNavigateBack: () -> Unit,
    viewModel: DepositViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar depósito") },
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "¿Cuántas botellas reciclaste?")

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { viewModel.onDecrement() },
                    enabled = !state.isSaving
                ) {
                    Text(text = "-")
                }

                Text(
                    text = state.cantidadBotellas.toString(),
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                OutlinedButton(
                    onClick = { viewModel.onIncrement() },
                    enabled = !state.isSaving
                ) {
                    Text(text = "+")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.onRegisterDeposit() },
                enabled = !state.isSaving
            ) {
                Text(text = "Registrar depósito")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (state.isSaving) {
                CircularProgressIndicator()
            }

            state.successMessage?.let { msg ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = msg)
            }

            state.errorMessage?.let { msg ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = msg)
            }
        }
    }
}
