package com.example.newspulse.ui.listen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.newspulse.navigation.Route
import com.example.newspulse.ui.components.homeComponents.MyLatestNewsPage
import com.example.newspulse.ui.components.profileoptionsScreens.SettingTopAppBar
import com.example.newspulse.ui.viewmodel.ListenViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListenScreen(
    onBackClick: () -> Unit,
    onNavigate: (Route) -> Unit,
    viewModel: ListenViewModel = hiltViewModel()
) {
    val isPlaying by viewModel.isPlaying.collectAsState()
    val progress by viewModel.progress.collectAsState()
    val currentArticle by viewModel.currentArticle.collectAsState()
    val playbackSpeed by viewModel.currentSpeed.collectAsState()
    val allArticles by viewModel.articles.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startRandomPlaylist()
    }

    val upNextArticles = remember(currentArticle, allArticles) {
        val index = allArticles.indexOfFirst { it.url == currentArticle?.url }
        if (index != -1 && index < allArticles.size - 1) {
            allArticles.subList(index + 1, allArticles.size)
        } else {
            allArticles.filter { it.url != currentArticle?.url }
        }
    }

    Scaffold(
        topBar = {
            SettingTopAppBar(
                title = "Audio Reader",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.surface),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .padding(20.dp)
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    if (currentArticle?.image != null) {
                        AsyncImage(
                            model = currentArticle?.image,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            placeholder = androidx.compose.ui.res.painterResource(id = com.example.newspulse.R.drawable.img_12)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Headset,
                                contentDescription = null,
                                modifier = Modifier.size(100.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.9f)),
                                    startY = 200f
                                )
                            )

                    )

                    //text on image
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(20.dp)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            //soruce name
                            Text(
                                text = currentArticle?.source?.name ?: "NEWS",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = currentArticle?.title ?: "title",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Slider for progress
                        Slider(
                            value = progress,
                            onValueChange = { },
                            colors = SliderDefaults.colors(
                                thumbColor = Color.White,
                                activeTrackColor = Color.White,
                                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            //speed

                            TextButton(onClick = { viewModel.cycleSpeed() }) {
                                Text("${playbackSpeed}x", color = Color.White, fontWeight = FontWeight.Bold)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { viewModel.playPrevious() }) {
                                    Icon(Icons.Default.SkipPrevious, "Prev", tint = Color.White)
                                }
                                Surface(
                                    modifier = Modifier.size(56.dp),
                                    shape = CircleShape,
                                    color = Color.White,
                                    onClick = { viewModel.togglePlayPause() }
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                            contentDescription = "Play/Pause",
                                            tint = Color.Black,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }
                                IconButton(onClick = { viewModel.playNext() }) {
                                    Icon(Icons.Default.SkipNext, "Next", tint = Color.White)
                                }
                            }

                            //detail sceen
                            IconButton(onClick = {
                                currentArticle?.let { article ->
                                    onNavigate(
                                        Route.MyDetailedArticleScreen(
                                            image = article.image,
                                            title = article.title,
                                            description = article.description,
                                            content = article.content,
                                            fromSaved = false
                                        )
                                    )
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                                    contentDescription = "Open Details",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }

            if (upNextArticles.isNotEmpty()) {
                item {
                    Text(
                        text = "Up Next",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
                    )
                }

                items(upNextArticles) { article ->
                    MyLatestNewsPage(
                        newsItem = article,
                        onNewsClick = { viewModel.setCurrentArticleAudio(it) }
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewListenScreen() {
    ListenScreen(
        onBackClick = {},
        onNavigate = {}
    )
}
