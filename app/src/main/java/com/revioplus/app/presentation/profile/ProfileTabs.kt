package com.revioplus.app.presentation.profile

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileTabs(
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit,
    onWalletClick: () -> Unit,
    onHistoryClick: () -> Unit
) {
    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = modifier
    ) {
        Tab(
            selected = selectedIndex == 0,
            onClick = onProfileClick,
            text = { Text("Perfil") }
        )
        Tab(
            selected = selectedIndex == 1,
            onClick = onWalletClick,
            text = { Text("Billetera") }
        )
        Tab(
            selected = selectedIndex == 2,
            onClick = onHistoryClick,
            text = { Text("Historial") }
        )
    }
}
