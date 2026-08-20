package com.example.newspulse.ui.components.profileoptionsScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newspulse.ui.Intent.ExploreIntent
import com.example.newspulse.ui.viewmodel.ExploreViewModel
import com.example.newspulse.ui.viewmodel.TypographyViewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MyInterestAndPreference(
    onBackClick: () -> Unit,
    exploreViewModel: ExploreViewModel = hiltViewModel(),
    typographyViewModel: TypographyViewModel = hiltViewModel()
) {
    val uiState by exploreViewModel.state.collectAsState()
    val selectedFont by typographyViewModel.selectedFont.collectAsState()
    
    val topics = uiState.availableTopics
    val selectedTopics = uiState.selectedTopics


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
                    text = "Customize your reading experience. Select topics and adjust typography to suit your style.",
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

            item {
                SectionTitle("TYPOGRAPHY")
            }

            item {
                var expanded by remember { mutableStateOf(false) }
                val typographyOptions = typographyViewModel.typographyOptions

                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = selectedFont,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                ),
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                textStyle = LocalTextStyle.current.copy(
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
                            ) {
                                typographyOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = {
                                            AutoText(
                                                text = option,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                baseFontSize = 14.sp
                                            )
                                        },
                                        onClick = {
                                            typographyViewModel.onFontSelected(option)
                                            expanded = false
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(20.dp))
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyInterestAndPreferencePreview() {
    MyInterestAndPreference(onBackClick = {})
}
