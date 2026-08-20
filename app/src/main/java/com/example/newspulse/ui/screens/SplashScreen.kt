package com.example.newspulse.ui.screens

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.newspulse.R

@Composable
fun SplashScreen(onVideoFinished: () -> Unit) {
    val context = LocalContext.current
    
    // remembers the URI to avoid reparsing on every recomposition
    val videoUri = remember {
        Uri.parse("android.resource://${context.packageName}/${R.raw.video2}")
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AndroidView(
            //factory->the block where videoview is created
            factory = { ctx ->
                object : VideoView(ctx) {
                    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
                        val width = getDefaultSize(0, widthMeasureSpec)
                        val height = getDefaultSize(0, heightMeasureSpec)
                        setMeasuredDimension(width, height)
                    }
                }.apply {
                    setVideoURI(videoUri)
                    //  Wait until prepared to reduce initial UI lag
                    setOnPreparedListener { start() }
                    setOnCompletionListener {
                        stopPlayback() //stop and clear the surface imediatly
                        onVideoFinished()
                    }
                }
            },
            modifier = Modifier.fillMaxSize(),
            onRelease = {it.stopPlayback()} // stop when the composable is removed
        )
    }
}
