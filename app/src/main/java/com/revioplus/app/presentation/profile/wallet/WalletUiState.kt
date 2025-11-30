package com.revioplus.app.presentation.profile.wallet

data class WalletUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,

    val saldoDisponibleText: String = "",
    val saldoBloqueadoText: String = "",
    val moneda: String = "",

    val limiteRetiroDiarioText: String = "",
    val montoMinimoRetiroText: String = "",
    val ultimaActualizacionText: String = "",

    val estadoText: String = ""
)