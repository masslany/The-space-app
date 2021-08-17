package com.masslany.thespaceapp.presentation.dashboard

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController


@Composable
fun DashboardScreen(navController: NavController) {
    val configuration = LocalConfiguration.current

    when(configuration.orientation) {
        ORIENTATION_LANDSCAPE -> {
            LandscapeDashboard(navController)
        }
        else -> {
            PortraitDashboard(navController)
        }

    }
}




