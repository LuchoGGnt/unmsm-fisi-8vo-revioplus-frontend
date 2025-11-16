package com.revioplus.app.presentation.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController


fun NavHostController.navigateBottomBar(route: String) {
    this.navigate(route) {
        // Volvemos al destino inicial del gráfico (Home) guardando estado
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Evita crear múltiples copias de la misma pantalla
        launchSingleTop = true
        // Restaura el estado (scroll, etc.) si ya habías visitado esa pantalla
        restoreState = true
    }
}