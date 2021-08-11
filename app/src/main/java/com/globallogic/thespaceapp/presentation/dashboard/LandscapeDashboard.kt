package com.globallogic.thespaceapp.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.globallogic.thespaceapp.R
import com.globallogic.thespaceapp.presentation.theme.DarkGray

@Composable
fun LandscapeDashboard(navController: NavController) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
            .horizontalScroll(scrollState)
    ) {

        DashboardItem(
            text = stringResource(id = R.string.launches),
            imageResource = R.drawable.launches_crop,
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
                .shadow(8.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            navController.navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToLaunchesFragment()
            )
        }
        
        DashboardItem(
            text = stringResource(id = R.string.dragons),
            imageResource = R.drawable.dragon_crop,
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
                .shadow(8.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            navController.navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToDragonsFragment()
            )
        }

        DashboardItem(
            text = stringResource(id = R.string.rockets),
            imageResource = R.drawable.rockets_crop,
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
                .shadow(8.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            navController.navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToRocketsFragment()
            )
        }

        DashboardItem(
            text = stringResource(id = R.string.roadster),
            imageResource = R.drawable.roadster_crop,
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
                .shadow(8.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            navController.navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToRoadsterDetailsFragment()
            )
        }

        DashboardItem(
            text = stringResource(id = R.string.starlink),
            imageResource = R.drawable.starlink,
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
                .shadow(8.dp)
                .clip(RoundedCornerShape(16.dp))
        ) {
            navController.navigate(
                DashboardFragmentDirections
                    .actionDashboardFragmentToStarlinkMapFragment()
            )
        }
    }

}
