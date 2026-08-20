package com.example.newspulse.ui.components.profileoptionsScreens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newspulse.ui.theme.*
import com.example.newspulse.ui.viewmodel.ThemeViewModel
import com.example.newspulse.R

@Composable
fun MyAppThemeScreen(themeViewModel: ThemeViewModel, onBackClick: () -> Unit) {
    val currentTheme by themeViewModel.appTheme.collectAsState()

    Scaffold(
        topBar = {
            SettingTopAppBar(
                title = "App Theme",
                onBackClick = onBackClick,
            )
        }
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .padding(16.dp),
        ) {
            Text(
                text="APPEARANCE MODE",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val lightWeight by animateFloatAsState(targetValue = if (currentTheme == AppTheme.LIGHT) 2f else 1f)
                val darkWeight by animateFloatAsState(targetValue = if (currentTheme == AppTheme.DARK) 2f else 1f)

                ThemeSelectionBox(
                    modifier = Modifier.weight(lightWeight),
                    icon = Icons.Default.WbSunny,
                    label = "Light",
                    isSelected = currentTheme == AppTheme.LIGHT,
                    backgroundColor = Color.White,
                    textColor = Color.Black,
                    onClick = { themeViewModel.setTheme(AppTheme.LIGHT) }
                )

                ThemeSelectionBox(
                    modifier = Modifier.weight(darkWeight),
                    icon = Icons.Default.Nightlight,
                    label = "Dark",
                    isSelected = currentTheme == AppTheme.DARK,
                    backgroundColor = Color(0xFF121212),
                    textColor = Color.White,
                    onClick = { themeViewModel.setTheme(AppTheme.DARK) }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "THEME PALETTE",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

           Column {
                customThemeBox(
                    painter = painterResource(id = R.drawable.img_7),
                    label = "Autumn",
                    isSelected = currentTheme == AppTheme.AUTUMN,
                    backgroundColor = Color.White,
                    textColor = AutumnOrange,
                    onClick = { themeViewModel.setTheme(AppTheme.AUTUMN) }
                )
               
               Spacer(modifier = Modifier.height(8.dp))

               customThemeBox(
                   painter = painterResource(id=R.drawable.img_9),
                   label = "Fairy Tale",
                   isSelected = currentTheme == AppTheme.FAIRY,
                   backgroundColor = Color.White,
                   textColor = Color(0xFF472B8A),
                   onClick = { themeViewModel.setTheme(AppTheme.FAIRY) }
               )

               Spacer(modifier = Modifier.height(8.dp))

               customThemeBox(
                   painter = painterResource(id = R.drawable.img_8),
                   label = "Rose",
                   isSelected = currentTheme == AppTheme.ROSE,
                   backgroundColor = Color.White,
                   textColor = Color(0xFFE91E63),
                   onClick = { themeViewModel.setTheme(AppTheme.ROSE) }
               )
            }
        }
    }
}

@Composable
fun ThemeSelectionBox(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
    val borderWidth = if (isSelected) 3.dp else 1.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(4.dp),
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(borderWidth, borderColor),
            modifier = Modifier.fillMaxWidth()
                .height(120.dp)
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
               Column(horizontalAlignment = Alignment.CenterHorizontally) {
                   Icon(
                       imageVector = icon,
                       contentDescription = null,
                       tint = textColor,
                       modifier = Modifier.size(32.dp)
                   )
                   Spacer(modifier = Modifier.height(4.dp))
                   Text(
                       text = label.uppercase(),
                       color = textColor,
                       style = MaterialTheme.typography.labelLarge
                   )
               }
            }
        }
    }
}

@Composable
fun customThemeBox(
    painter: androidx.compose.ui.graphics.painter.Painter,
    label: String,
    isSelected: Boolean,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray
    val borderWidth = if (isSelected) 3.dp else 1.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(borderWidth, borderColor),
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clickable { onClick() },
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
               Row(
                  modifier = Modifier
                      .fillMaxSize()
                      .padding(horizontal = 16.dp),
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   Image(
                       painter=painter,
                       contentDescription = null,
                       modifier = Modifier.size(40.dp)
                   )
                   Spacer(modifier = Modifier.width(16.dp))
                   Text(
                       text = label,
                       color = textColor,
                       fontSize = 20.sp,
                       modifier = Modifier.weight(1f)
                   )

                   RadioButton(
                       selected = isSelected,
                       onClick = { onClick() }
                   )
               }
            }
        }
    }
}
