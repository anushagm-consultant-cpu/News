package com.example.newspulse.ui.screens

import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newspulse.data.NewsItem
import com.example.newspulse.ui.components.profileoptionsScreens.AutoText
import com.example.newspulse.ui.components.detailArticle.ImagePartDeatiledArticle
import com.example.newspulse.ui.components.detailArticle.MyAuthorView
import com.example.newspulse.ui.components.detailArticle.likeandComment

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyDetailedArticleScreen(
    article: NewsItem,
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
    ) {
        item {
            ImagePartDeatiledArticle(article)
        }
        item {
            MyAuthorView(article)
        }
        item {
            Column(
                modifier = Modifier.padding( top = 24.dp, bottom = 24.dp, start = 20.dp, end = 20.dp)
            ) {
                val cleanDescription = Html.fromHtml(
                    article.description ?: "",
                    Html.FROM_HTML_MODE_LEGACY
                ).toString()

                val cleanContent = Html.fromHtml(
                    article.content ?: "",
                    Html.FROM_HTML_MODE_LEGACY
                ).toString()

                AutoText(
                    text = cleanContent ,
                    baseFontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Justify,
                    fontFamily = FontFamily.Serif
                )
                AutoText(
                    text = cleanDescription,
                    baseFontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Justify,
                    fontFamily = FontFamily.Serif
                )
                AutoText(
                    text = cleanContent,
                    baseFontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Justify,
                    fontFamily = FontFamily.Serif
                )
                AutoText(
                    text = cleanDescription,
                    baseFontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Justify,
                    fontFamily = FontFamily.Serif
                )
                AutoText(
                    text = cleanContent,
                    baseFontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Justify,
                    fontFamily = FontFamily.Serif
                )
                AutoText(
                    text = cleanDescription,
                    baseFontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Justify,
                    fontFamily = FontFamily.Serif
                )

            }
        }
        item {
            likeandComment()
        }
    }
}
