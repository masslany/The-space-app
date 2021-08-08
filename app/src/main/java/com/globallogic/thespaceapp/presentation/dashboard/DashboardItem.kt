package com.globallogic.thespaceapp.presentation.dashboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.globallogic.thespaceapp.R


@Composable
fun DashboardItem(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes imageResource: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1.0f)
            .clickable {
                onClick()
            }
    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = size.height / 3,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.DstOut)
                    }
                },
            contentScale = ContentScale.Crop

        )
        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(4.dp),
            color = Color.White,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun DashboardItemPreview() {
    DashboardItem(
        text = "Launches",
        imageResource = R.drawable.launches_crop
    ) {}
}