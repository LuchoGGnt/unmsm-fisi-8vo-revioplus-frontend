package com.revioplus.app.presentation.login

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val loginSuccess: Boolean = false,

    val email: String = "",
    val password: String = ""
)
