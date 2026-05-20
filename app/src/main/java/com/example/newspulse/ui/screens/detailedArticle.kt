package com.example.newspulse.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newspulse.R
import com.example.newspulse.ui.components.detailArticle.ImagePartDeatiledArticle
import com.example.newspulse.ui.components.detailArticle.MyAuthorView
import com.example.newspulse.ui.components.detailArticle.likeandComment


@Composable
fun MyDetailedArticleScreen(image: Int,title: String, description: String, content: String) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),


        ) {
        item {
            ImagePartDeatiledArticle(image,title, description)

        }
        item {
            MyAuthorView()

        }
        item {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Text(
                    text = content,
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Justify,
                    fontFamily = FontFamily.Serif
                )
            }
        }
        item {
            likeandComment()

        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyPreviewOfDetailArticle() {
    MaterialTheme {
        MyDetailedArticleScreen(
            image = R.drawable.img_1,
            "Business",
            "Bitcoin: The Trendsetter Shaping the Future of Crypto Investment \n",
            content = "Btcoin has oncan ce again asserted its dominance in the global financial landscape, transcending its initial identity as a mere digital currency to become a foundational asset class for institutional investors. As we move further into 2024, the ripples of its recent technological upgrades and the stabilizing regulatory environment are creating a new paradigm for how the world perceives value and decentralized trust.\n" +
                    "\n" +
                    "The narrative surrounding cryptocurrency is shifting from speculative volatility toward strategic asset allocation. Financial institutions that once remained on the sidelines are now integrating blockchain-based assets into their diversified portfolios, citing Bitcoin's role as a potential hedge against traditional market fluctuations and inflationary pressures.\n" +
                    "\n" +
                    "\"Bitcoin is no longer an experiment; it is the benchmark for the next generation of financial infrastructure.\"\n" +
                    "\n" +
                    "Industry experts point to the \"halving\" events and the introduction of spot ETFs as critical catalysts. These mechanisms have not only reduced the available supply but have also democratized access for millions of retail investors who prefer the security of traditional brokerage accounts over direct wallet management.\n" +
                    "\n" +
                    "However, the road ahead is not without challenges. Sustainability remains a central theme in the discourse, with increased pressure on mining operations to transition toward renewable energy sources. The intersection of environmental responsibility and digital scarcity will likely define the next chapter of the Bitcoin story."

        )
    }
}