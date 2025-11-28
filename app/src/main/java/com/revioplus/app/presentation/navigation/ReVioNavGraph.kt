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
        composable(NavRoute.Home.route) {
            HomeScreen(
                onNavigateToDeposit = {
                    navController.navigateBottomBar(NavRoute.Deposit.route)
                },
                onNavigateToProfile = {
                    navController.navigateBottomBar(NavRoute.Profile.route)
                }
            )
        }

        composable(NavRoute.Deposit.route) {
            DepositScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(NavRoute.Profile.route) {
            ProfileScreen(
                onNavigateToWallet = {
                    navController.navigate(NavRoute.ProfileWallet.route)
                }
            )
        }

        composable(NavRoute.ProfileWallet.route) {
            WalletScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
