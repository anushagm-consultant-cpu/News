package com.example.newspulse.ui.components.detailArticle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newspulse.data.NewsItem
import com.example.newspulse.data.calculateReadingTime
import com.example.newspulse.ui.components.profileoptionsScreens.AutoText

@Composable
fun ImagePartDeatiledArticle(article: NewsItem){
    val backgroundColor = MaterialTheme.colorScheme.background
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min=500.dp)
    ) {
        AsyncImage(
            model = article.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )

        // The Theme-aware Gradient Overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            backgroundColor.copy(alpha = 0.6f),
                            backgroundColor 
                        ),
                        startY = 600f,

                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(top=300.dp)
                .padding(horizontal = 20.dp)
                .padding(bottom = 0.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AutoText(
                    text = article.source?.name ?: "Unknown Source".uppercase(),
                    color = if (MaterialTheme.colorScheme.background != Color.White) MaterialTheme.colorScheme.secondary else Color.White,
                    baseFontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(
                            color = if (MaterialTheme.colorScheme.background != Color.White) Color.White else MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(24.dp)
                        ).padding(horizontal = 10.dp, vertical = 5.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))


                AutoText(
                    text = calculateReadingTime(article.description, article.content),
                    color = Color.DarkGray,
                    baseFontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    style =
                    MaterialTheme.typography.displaySmall.copy(lineHeight = 1.2.em,
                    shadow = Shadow(
                        color = Color.White.copy(alpha = 0.3f),
                        offset = Offset(2f,2f),
                        blurRadius = 4f
                    )))


            }

            Spacer(modifier = Modifier.height(10.dp))

            AutoText(
                text = article.title,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                baseFontSize = 32.sp,

               style = MaterialTheme.typography.displaySmall.copy(lineHeight = 1.2.em,

                    )
           )
        }
    }
}
