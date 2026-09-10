package com.examen.civique.ui.splash

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.examen.civique.R
import kotlinx.coroutines.delay

@Composable
fun BrandSplashScreen(
    elapsedMillis: () -> Long,
    reducedMotion: Boolean,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    var elapsed by remember { mutableLongStateOf(elapsedMillis()) }
    val state = BrandSplashTimeline.frameAt(elapsed, reducedMotion)

    LaunchedEffect(reducedMotion) {
        while (!BrandSplashTimeline.frameAt(elapsedMillis(), reducedMotion).finished) {
            elapsed = elapsedMillis()
            delay(16L)
        }
        elapsed = elapsedMillis()
        onFinished()
    }

    Box(
        modifier.fillMaxSize().background(Color.Black).testTag("brand_splash")
            .alpha(state.contentAlpha),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.exci_logo),
            contentDescription = "EXCI",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxWidth(0.78f).widthIn(max = 500.dp)
                .alpha(state.logoAlpha).scale(state.logoScale)
        )
        Image(
            painter = painterResource(pigeonDrawable(state.pigeonFrame)),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.sizeIn(maxWidth = 190.dp, maxHeight = 190.dp)
                .fillMaxWidth(0.38f).aspectRatio(1f).alpha(state.pigeonAlpha)
                .graphicsLayer {
                    translationX = state.pigeonOffsetX
                    translationY = state.pigeonOffsetY
                    scaleY = state.pigeonScaleY
                }
        )
    }
}

@DrawableRes
private fun pigeonDrawable(frame: Int): Int = when (frame) {
    1 -> R.drawable.pigeon_frame_2
    2 -> R.drawable.pigeon_frame_3
    3 -> R.drawable.pigeon_frame_4
    4 -> R.drawable.pigeon_frame_5
    5 -> R.drawable.pigeon_frame_6
    6 -> R.drawable.pigeon_frame_7
    else -> R.drawable.pigeon_frame_1
}
