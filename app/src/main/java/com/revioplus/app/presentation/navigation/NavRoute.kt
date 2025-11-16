package com.revioplus.app.presentation.navigation

sealed class NavRoute(val route: String) {
    data object Home : NavRoute("home")
    data object Deposit : NavRoute("deposit")
    data object Profile : NavRoute("profile")
}