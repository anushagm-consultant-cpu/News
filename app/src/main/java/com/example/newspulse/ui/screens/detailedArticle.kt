package com.example.newspulse.ui.screens

import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newspulse.data.NewsItem
import com.example.newspulse.ui.components.profileoptionsScreens.AutoText
import com.example.newspulse.ui.components.detailArticle.ImagePartDeatiledArticle
import com.example.newspulse.ui.components.detailArticle.MyAuthorView
import com.example.newspulse.ui.components.detailArticle.likeandComment
import com.example.newspulse.ui.components.detailArticle.DoubleTapSaveContainer
import com.example.newspulse.ui.viewmodel.ListenViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

private data class EmojiAnimationData(
    val offset: Offset,
    val scale: Float,
    val rotation: Float
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyDetailedArticleScreen(
    article: NewsItem,
    isSaved: Boolean,
    onBack: () -> Unit,
    onDoubleTap: () -> Unit,
    listenViewModel: ListenViewModel = hiltViewModel(),
    externalEmoji: String? = null,
    onEmojiConsumed: () -> Unit = {}
) {
    var animatingEmoji by remember { mutableStateOf<String?>(null) }
    var emojiInstances by remember { mutableStateOf<List<EmojiAnimationData>>(emptyList()) }


    val scrollState = rememberLazyListState()

    //  Reading Progress Indicator
    val readingProgress by remember {
        derivedStateOf {
            val layoutInfo = scrollState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            if (totalItems <= 1) return@derivedStateOf 0f
            

            if (!scrollState.canScrollForward) return@derivedStateOf 1f
            
            val firstVisibleItem = layoutInfo.visibleItemsInfo.firstOrNull()
            if (firstVisibleItem == null) 0f
            else {
                val index = firstVisibleItem.index
                val offset = scrollState.firstVisibleItemScrollOffset.toFloat()
                val size = firstVisibleItem.size.toFloat()

                ((index + offset / size) / (totalItems - 1)).coerceIn(0f, 1f)
            }
        }
    }

    LaunchedEffect(externalEmoji) {
        if (externalEmoji != null) {
            animatingEmoji = externalEmoji
            emojiInstances = List(10) {
                EmojiAnimationData(
                    offset = Offset(Random.nextFloat(), Random.nextFloat()),
                    scale = Random.nextFloat() * 0.8f + 0.5f,
                    rotation = Random.nextFloat() * 60f - 30f
                )
            }
            onEmojiConsumed()
        }
    }

    LaunchedEffect(animatingEmoji) {
        if (animatingEmoji != null) {
            delay(600) // delay for faster disappearance
            animatingEmoji = null
        }
    }
    DoubleTapSaveContainer(
        isSaved = isSaved,
        onDoubleTap = onDoubleTap
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = scrollState
            ) {
                item {
                    ImagePartDeatiledArticle(
                        article = article,

                    )
                }
                item {
                    MyAuthorView(article)
                }
                item {
                    Column(
                        modifier = Modifier.padding(
                            top = 24.dp,
                            bottom = 24.dp,
                            start = 20.dp,
                            end=16.dp
                        )
                    ) {
                        val cleanDescription = Html.fromHtml(
                            article.description ?: "",
                            Html.FROM_HTML_MODE_LEGACY
                        ).toString()

                        val cleanContent = Html.fromHtml(
                            article.content ?: "",
                            Html.FROM_HTML_MODE_LEGACY
                        ).toString()

                        val texts = listOf(
                            cleanContent,
                            cleanDescription,
                            cleanContent,
                            cleanDescription,
                            cleanContent,
                            cleanDescription
                        )
                        texts.forEach { text ->
                            AutoText(
                                text = text,
                                baseFontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    lineHeight = 1.4.em,

                                )
                            )
                        }
                    }
                }
                item {
                    likeandComment(onEmojiReaction = { emoji ->
                        animatingEmoji = emoji
                        emojiInstances = List(10) {
                            EmojiAnimationData(
                                offset = Offset(Random.nextFloat(), Random.nextFloat()),
                                scale = Random.nextFloat() * 0.8f + 0.5f,
                                rotation = Random.nextFloat() * 60f - 30f
                            )
                        }
                    })
                }
            }

            // Reading Progress Indicator
            LinearProgressIndicator(
                progress = { readingProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .align(Alignment.TopCenter),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.Transparent
            )

            // Full screen emoji explosion animation overlaying the content
            AnimatedVisibility(
                visible = animatingEmoji != null,
                enter = fadeIn(animationSpec = tween(400)) + scaleIn(
                    initialScale = 0.5f,
                    animationSpec = tween(400)
                ),
                exit = fadeOut(animationSpec = tween(400)) + scaleOut(
                    targetScale = 1.5f,
                    animationSpec = tween(400)
                ),
                modifier = Modifier.fillMaxSize()
            ) {
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val width = maxWidth
                    val height = maxHeight
                    animatingEmoji?.let { emoji ->
                        emojiInstances.forEach { data ->
                            Text(
                                text = emoji,
                                fontSize = 40.sp,
                                modifier = Modifier
                                    .offset(
                                        x = width * data.offset.x - 20.dp,
                                        y = height * data.offset.y - 20.dp
                                    )
                                    .scale(data.scale)
                                    .rotate(data.rotation)
                            )
                        }
                    }
                }
            }
        }
    }
}
