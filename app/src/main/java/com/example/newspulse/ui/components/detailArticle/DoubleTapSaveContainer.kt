package com.example.newspulse.ui.components.detailArticle

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun DoubleTapSaveContainer(
    modifier: Modifier = Modifier,
    isSaved: Boolean,
    onDoubleTap: () -> Unit,
    content: @Composable () -> Unit
) {
    var showAnimation by remember { mutableStateOf(false) }
    var tapOffset by remember { mutableStateOf(Offset.Zero) }

    val density = LocalDensity.current
    val iconSize = 100.dp
    val iconSizePx = with(density) { iconSize.toPx() }

    // --- Dancing Logic ---
    val infiniteTransition = rememberInfiniteTransition(label = "dance")
    val rotation by infiniteTransition.animateFloat(
        initialValue = -15f,
        targetValue = 15f,
        animationSpec = infiniteRepeatable(
            animation = tween(150, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )

    val danceScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    LaunchedEffect(showAnimation) {
        if (showAnimation) {
            delay(800)
            showAnimation = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { offset ->
                        tapOffset = offset
                        showAnimation = true
                        onDoubleTap()
                    }
                )
            }
    ) {
        content()

        AnimatedVisibility(
            visible = showAnimation,
            enter = fadeIn(animationSpec = tween(200)) + scaleIn(initialScale = 0.4f, animationSpec = tween(400)),
            exit = fadeOut(animationSpec = tween(200)) + scaleOut(targetScale = 1.6f, animationSpec = tween(400)),
            modifier = Modifier.offset {
                IntOffset(
                    x = (tapOffset.x - iconSizePx / 2).toInt(),
                    y = (tapOffset.y - iconSizePx / 2).toInt()
                )
            }
        ) {
            Box(
                modifier = Modifier.size(iconSize),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Icon(
                    imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .size(60.dp)
                        .graphicsLayer {
                            rotationZ = rotation
                            scaleX = danceScale
                            scaleY = danceScale
                        }
                )
            }
        }
    }
}