package com.example.newspulse.ui.components.profileoptionsScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newspulse.ui.Intent.ExploreIntent
import com.example.newspulse.ui.viewmodel.ExploreViewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MyInterestAndPreference(
    onBackClick: () -> Unit,
    exploreViewModel: ExploreViewModel = hiltViewModel()
) {
    val uiState by exploreViewModel.state.collectAsState()
    
    val topics = uiState.availableTopics
    val selectedTopics = uiState.selectedTopics
    
    var articleLength by remember { mutableStateOf("Short") }

    Scaffold(
        topBar = {
            SettingTopAppBar(
                title = "Interest & Preference",
                onBackClick = onBackClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item {
                Spacer(modifier = Modifier.height(10.dp))

                AutoText(
                    text = "Customize your reading experience. Select the topics you care about most to personalize your feed.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    baseFontSize = 15.sp
                )
            }

            item {
                SectionTitle("CHOOSE YOUR INTERESTS")
            }

            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    FlowRow(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        topics.forEach { topic ->
                            val isSelected = selectedTopics.contains(topic)
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    exploreViewModel.onIntent(ExploreIntent.ToggleTopicSelection(topic))
                                },
                                label = {
                                    AutoText(
                                        text = topic,
                                        baseFontSize = 14.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                ),
                                border = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    AutoText(
        text = title,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        baseFontSize = 13.sp
    )
}

