package com.example.newspulse.ui.components.detailArticle

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuteSliderBottomSheet(
    topic: String,
    onDismiss: () -> Unit,
    onConfirm: (duration: String, intensity: String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var selectedDuration by remember { mutableStateOf("24 Hours") }
    var intensityValue by remember { mutableFloatStateOf(0f) } // 0 for Light, 1 for Hard

    val durations = listOf("24 Hours", "7 Days", "30 Days", "Indefinitely")

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 40.dp)
        ) {
            Text(
                text = "Mute stories about #$topic",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Time Duration",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                durations.forEach { duration ->
                    FilterChip(
                        selected = selectedDuration == duration,
                        onClick = { selectedDuration = duration },
                        label = { Text(duration, fontSize = 12.sp) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Intensity: ${if (intensityValue < 0.5f) "Light Mute" else "Hard Mute"}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Slider(
                value = intensityValue,
                onValueChange = { intensityValue = it },
                valueRange = 0f..1f,
                steps = 0 // Just two states: Light or Hard
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Light: Hides opinions",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Hard: Total blackout",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = androidx.compose.ui.text.style.TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val intensity = if (intensityValue < 0.5f) "Light" else "Hard"
                    onConfirm(selectedDuration, intensity)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirm Mute")
            }
        }
    }
}
