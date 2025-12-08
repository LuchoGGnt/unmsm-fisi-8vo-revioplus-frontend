package com.revioplus.app.presentation.navigation

sealed class NavRoute(val route: String) {
    data object Login : NavRoute("login")
    data object Home : NavRoute("home")
    data object Deposit : NavRoute("deposit")
    data object Profile : NavRoute("profile")

    // Sub-rutas de Perfil
    data object ProfileWallet : NavRoute("profile/wallet")
    data object ProfileHistory : NavRoute("profile/history")
}
