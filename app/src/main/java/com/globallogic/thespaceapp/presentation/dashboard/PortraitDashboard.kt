package com.globallogic.thespaceapp.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
fun PortraitDashboard(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            DashboardItem(
                text = stringResource(id = R.string.launches),
                imageResource = R.drawable.launches_crop,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(end = 16.dp)
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
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .shadow(8.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                navController.navigate(
                    DashboardFragmentDirections
                        .actionDashboardFragmentToDragonsFragment()
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            DashboardItem(
                text = stringResource(id = R.string.rockets),
                imageResource = R.drawable.rockets_crop,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(end = 16.dp)
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
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .shadow(8.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                navController.navigate(
                    DashboardFragmentDirections
                        .actionDashboardFragmentToRoadsterDetailsFragment()
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            DashboardItem(
                text = stringResource(id = R.string.starlink),
                imageResource = R.drawable.starlink,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(end = 16.dp)
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
}
