package com.revioplus.app.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.revioplus.app.presentation.home.HomeScreen
import com.revioplus.app.presentation.deposit.DepositScreen
import com.revioplus.app.presentation.profile.ProfileScreen

@Composable
fun ReVioNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Home.route,         // Pantalla inicial
        modifier = modifier
    ) {
        composable(NavRoute.Home.route) {
            HomeScreen(
                onNavigateToDeposit = {
//                    navController.navigate(NavRoute.Deposit.route)
                    navController.navigateBottomBar(NavRoute.Deposit.route)
                },
                onNavigateToProfile = {
//                    navController.navigate(NavRoute.Profile.route)
                    navController.navigateBottomBar(NavRoute.Profile.route)
                }
            )
        }
        composable(NavRoute.Deposit.route) {
            DepositScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavRoute.Profile.route) {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}