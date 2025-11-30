package com.revioplus.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.revioplus.app.presentation.home.HomeScreen
import com.revioplus.app.presentation.deposit.DepositScreen
import com.revioplus.app.presentation.profile.ProfileScreen
import com.revioplus.app.presentation.profile.wallet.WalletScreen
import com.revioplus.app.presentation.profile.history.ProfileHistoryScreen

@Composable
fun ReVioNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Home.route,
        modifier = modifier
    ) {

        // Vista principal: Inicio (Home)
        composable(NavRoute.Home.route) {
            HomeScreen(
                onNavigateToDeposit = {
                    // Navega al tab inferior "Depositar"
                    navController.navigateBottomBar(NavRoute.Deposit.route)
                },
                onNavigateToProfile = {
                    // Navega al tab inferior "Perfil"
                    navController.navigateBottomBar(NavRoute.Profile.route)
                }
            )
        }

        // Vista principal: Depositar (pantalla de depósito)
        composable(NavRoute.Deposit.route) {
            DepositScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Vista principal: Perfil (Perfil / información del usuario)
        composable(NavRoute.Profile.route) {
            ProfileScreen(
                onNavigateToWallet = {
                    // Desde Perfil → Perfil/Billetera
                    navController.navigate(NavRoute.ProfileWallet.route)
                },
                onNavigateToHistory = {
                    // Desde Perfil → Perfil/Historial
                    navController.navigate(NavRoute.ProfileHistory.route)
                }
            )
        }

        // Vista interna de Perfil: Perfil / Billetera
        composable(NavRoute.ProfileWallet.route) {
            WalletScreen(
                // Flecha atrás o pestaña "Perfil" → volver a lo anterior (normalmente Perfil)
                onNavigateBack = { navController.popBackStack() },
                // Pestaña "Historial" en la barra de pestañas → Perfil/Historial
                onNavigateToHistory = {
                    navController.navigate(NavRoute.ProfileHistory.route)
                }
            )
        }

        // Vista interna de Perfil: Perfil / Historial de depósitos
        composable(NavRoute.ProfileHistory.route) {
            ProfileHistoryScreen(
                // Flecha atrás o pestaña "Perfil" → volver a lo anterior (Perfil o Billetera, según de dónde vengas)
                onNavigateBackToProfile = { navController.popBackStack() },
                // Pestaña "Billetera" en la barra de pestañas → Perfil/Billetera
                onNavigateToWallet = {
                    navController.navigate(NavRoute.ProfileWallet.route)
                }
            )
        }
    }
}
