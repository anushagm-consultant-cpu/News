package com.example.newspulse.ui.screens


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newspulse.navigation.Route
import com.example.newspulse.ui.components.homeComponents.MyLatestNewsPage
import com.example.newspulse.ui.viewmodel.ExploreViewModel
import com.example.newspulse.R
import com.example.newspulse.ui.Intent.ExploreIntent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExploreScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    exploreViewModel: ExploreViewModel = hiltViewModel()
) {

    val uiState by exploreViewModel.state.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        if (uiState.searchResults.isEmpty()) {
            exploreViewModel.onIntent(ExploreIntent.ClearSearch)
        }
    }

    val selectedTopics = uiState.selectedTopics.toList()
    
    val categories = selectedTopics.mapIndexed { index, topic ->
        val patternIndex = index % 7
        CategoryItem(
            name = topic,
            imgRes = getCategoryImage(topic),
            height = when (patternIndex) {
                0 -> 120.dp
                1 -> 290.dp
                2 -> 120.dp
                3 -> 155.dp
                4 -> 120.dp
                5 -> 160.dp
                6 -> 160.dp
                else -> 140.dp
            },
            isFullWidth = patternIndex == 0 || patternIndex == 4
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                if (it.length > 2) {
                    exploreViewModel.onIntent(ExploreIntent.SearchNews(it))
                } else if (it.isEmpty()) {
                    exploreViewModel.onIntent(ExploreIntent.ClearSearch)
                }
            },
            placeholder = { Text("Search for news...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = ""
                        exploreViewModel.onIntent(ExploreIntent.ClearSearch)
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.isSearching) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (searchQuery.isNotEmpty()) {
            if (uiState.searchResults.isNotEmpty()) {
                Text(
                    text = "Search Results",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(uiState.searchResults) { news ->
                        MyLatestNewsPage(
                            newsItem = news,
                            onNewsClick = {
                                navController.navigate(
                                    Route.MyDetailedArticleScreen(
                                        image = news.image,
                                        title = news.title,
                                        description = news.description,
                                        content = news.content
                                    )
                                )
                            }
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No matching news",
                        fontSize = 18.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            Text(
                text = "Browse Categories",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.primary
            )

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalItemSpacing = 12.dp,
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    categories,
                    span = { category ->
                        if (category.isFullWidth) StaggeredGridItemSpan.FullLine else StaggeredGridItemSpan.SingleLane
                    }
                ) { category ->
                    CategoryCard(category) {
                        searchQuery = category.name
                        exploreViewModel.onIntent(ExploreIntent.SearchNews(category.name))
                    }
                }
            }
        }
    }
}

fun getCategoryImage(topic: String): Int {
    return when (topic) {
        "Technology" -> R.drawable.technology
        "Sports" -> R.drawable.sports
        "Business" -> R.drawable.img_13
        "Health" -> R.drawable.img_14
        "Science" -> R.drawable.science
        "Politics" -> R.drawable.politics
        "Nature" -> R.drawable.nature
        "Entertainment" -> R.drawable.enterenmaint
        "Travel" -> R.drawable.travel
        "World"->R.drawable.world
        "Education"->R.drawable.education
        "Lifestyle"->R.drawable.lifestyle
        else -> R.drawable.img_1 // Default image
    }
}

@Composable
fun CategoryCard(category: CategoryItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(category.height)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            androidx.compose.foundation.Image(
                painter = painterResource(id = category.imgRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                              Color.Black.copy(alpha = 0.5f)

                            ),

                        )
                    )
            )

            Text(
                text = category.name,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            )
        }
    }
}

data class CategoryItem(
    val name: String,
    val imgRes : Int,
    val height: Dp,
    val isFullWidth: Boolean = false
)

