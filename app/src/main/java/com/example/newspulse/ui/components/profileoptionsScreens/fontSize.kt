package com.example.newspulse.ui.components.profileoptionsScreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newspulse.ui.viewmodel.FontViewModel
import com.example.newspulse.ui.viewmodel.LocalFontScale

@Composable
fun DemoSlider(sliderPosition: Float, onPositionChange: (Float) -> Unit) {
    Slider(
        modifier = Modifier.padding(horizontal = 30.dp),
        valueRange = 15f..30f,
        value = sliderPosition,
        onValueChange = onPositionChange
    )
}

@Composable
fun DemoFrontSizeScreen(onBackClick: () -> Unit,
                        viewModel: FontViewModel ) {

    var sliderPosition = viewModel.sliderValue

    CompositionLocalProvider(LocalFontScale provides viewModel.fontScale) {
        Scaffold(
            topBar = {
                SettingTopAppBar(
                    title = "Font Size",
                    onBackClick = onBackClick,
                )
            }
        ) { innerpadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,

                modifier = Modifier.fillMaxSize().padding(innerpadding)
            ) {
                // PREVIEW AREA
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AutoText(
                        text = "Welcome to News Pulse",
                        baseFontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = (24f * 1.2f).sp
                        )
                    AutoText(
                        text = "Reading should always feel comfortable. Adjust the font size using the slider below until the text is easy to read. Your preference will be applied throughout the app for a consistent reading experience.",
                        baseFontSize = 16.sp,
                        lineHeight = (16f * 1.2f).sp
                        )


                }

                Spacer(modifier = Modifier.height(60.dp))

                // SLIDER
                DemoSlider(
                    sliderPosition = sliderPosition,
                    onPositionChange = { viewModel.updateValue(it) }
                )

                // FOOTER LABELS
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Small", fontSize = 12.sp)
                    Text("Large", fontSize = 12.sp)
                }
            }
        }
    }
}
