package com.example.newspulse.ui.components.detailArticle

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newspulse.R

@Composable
fun ImagePartDeatiledArticle(image: Int,title: String, description: String){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)


    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        //The White Gradient Overlay (merge)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.White.copy(alpha = 0.5f),
                            Color.White // Solid white at the bottom to merge
                        ),
                        // Gradient starts  at 60% of the image height
                        startY = 600f
                    )
                )
        )
        Column(
            modifier = Modifier.padding(top = 330.dp, start = 20.dp, end = 20.dp)
        ) {
            Row() {
                Box(
                    modifier = Modifier
                        .background(
                            color = colorResource(R.color.teal_700),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 10.dp)
                ) {
                    Text(text = title.uppercase(), color = Color.White, fontSize = 13.sp)
                }
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "5 min read".uppercase(),
                    color = Color.DarkGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = description,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                style = TextStyle(lineHeight = 30.sp)
            )
        }
    }
}