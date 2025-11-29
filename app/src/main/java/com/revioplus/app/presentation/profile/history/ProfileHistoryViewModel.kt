package com.revioplus.app.presentation.profile.history

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileHistoryViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfileHistoryUiState(
            isLoading = false,
            errorMessage = null,
            recentDeposits = listOf(
                DepositHistoryItemUi(
                    id = "1",
                    bottles = 8,
                    location = "Centro Comercial Jockey Plaza",
                    dateText = "20/01/2024",
                    xpEarnedText = "+80 XP",
                    co2SavedText = "+4.0 kg CO₂"
                ),
                DepositHistoryItemUi(
                    id = "2",
                    bottles = 5,
                    location = "Parque Kennedy, Miraflores",
                    dateText = "19/01/2024",
                    xpEarnedText = "+50 XP",
                    co2SavedText = "+2.5 kg CO₂"
                )
            ),
            totalDepositsThisMonthText = "13",
            totalBottlesThisMonthText = "65"
        )
    )

    val uiState: StateFlow<ProfileHistoryUiState> = _uiState
}
