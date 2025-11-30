package com.revioplus.app.presentation.profile

data class ProfileUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,

    val displayName: String = "",
    val email: String = "",
    val phone: String = "",
    val city: String = "",
    val department: String = "",
    val country: String = "",
    val memberSince: String = "",

    val levelNumber: Int = 1,
    val levelTitle: String = "",
    val xpTotal: Long = 0L,

    val totalBottles: Long = 0L,
    val totalCo2Kg: Double = 0.0,

    val hasNfcEnabled: Boolean = false
)
