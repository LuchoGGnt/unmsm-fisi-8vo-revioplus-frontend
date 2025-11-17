package com.revioplus.app.presentation.home

data class HomeUiState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val city: String = "",
    val levelNumber: Int = 1,
    val levelTitle: String = "",
    val xpCurrent: Long = 0L,
    val xpNext: Long = 0L,
    val bottles: Long = 0L,
    val co2Kg: Double = 0.0,
    val challengeTitle: String = "",
    val challengeProgress: Int = 0,
    val challengeGoal: Int = 0,
    val recentDepositsText: List<String> = emptyList(),
    val errorMessage: String? = null
)