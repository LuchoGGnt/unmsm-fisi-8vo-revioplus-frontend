package com.revioplus.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.revioplus.app.presentation.components.BottomNavItem
import com.revioplus.app.presentation.components.ReVioBottomBar
import com.revioplus.app.presentation.navigation.NavRoute
import com.revioplus.app.presentation.navigation.ReVioNavGraph
import com.revioplus.app.presentation.theme.ReVioPlusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            ReVioPlusTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
//            }
            ReVioPlusAppRoot()
        }
    }
}

@Composable
fun ReVioPlusAppRoot() {
    ReVioPlusTheme {
        val navController = rememberNavController()

        val bottomDestinations = listOf(
            BottomNavItem(
                route = NavRoute.Home.route,
                label = "Inicio",
                icon = Icons.Filled.Home
            ),
            BottomNavItem(
                route = NavRoute.Deposit.route,
                label = "Depositar",
                icon = Icons.Filled.AddCircle
            ),
            BottomNavItem(
                route = NavRoute.Profile.route,
                label = "Perfil",
                icon = Icons.Filled.AccountCircle
            )
        )

        Scaffold(
            bottomBar = {
                ReVioBottomBar(
                    navController = navController,
                    destinations = bottomDestinations
                )
            }
        ) { innerPadding ->
            ReVioNavGraph(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}