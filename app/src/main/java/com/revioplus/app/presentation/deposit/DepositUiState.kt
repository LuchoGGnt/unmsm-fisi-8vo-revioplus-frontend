package com.revioplus.app.presentation.deposit

data class DepositUiState(
    val cantidadBotellas: Int = 1,
    val isSaving: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val xpGanado: Long? = null,
    val co2Ahorrado: Double? = null
)