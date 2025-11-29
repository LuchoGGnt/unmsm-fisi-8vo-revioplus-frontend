package com.revioplus.app.presentation.profile.history

data class DepositHistoryItemUi(
    val id: String,
    val bottles: Int,
    val location: String,
    val dateText: String,
    val xpEarnedText: String,
    val co2SavedText: String
)

data class ProfileHistoryUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val recentDeposits: List<DepositHistoryItemUi> = emptyList(),
    val totalDepositsThisMonthText: String = "",
    val totalBottlesThisMonthText: String = ""
)
