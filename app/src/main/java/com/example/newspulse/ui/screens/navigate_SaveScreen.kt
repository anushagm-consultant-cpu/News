package com.example.newspulse.ui.screens

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newspulse.navigation.Route
import com.example.newspulse.ui.components.homeComponents.MyLatestNewsPage
import com.example.newspulse.ui.viewmodel.SavedViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SaveScreen(
    navController: NavController,
    viewModel: SavedViewModel,
    innerPadding: PaddingValues
) {

    val savedNews by viewModel.savedNews.collectAsState()
    val isSelectionMode by viewModel.isSelectionMode.collectAsState()
    val selectedArticles by viewModel.selectedArticles.collectAsState()

    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var deleteMode by remember { mutableStateOf("all") } // "all" or "selected"

    BackHandler(enabled = isSelectionMode) {
        viewModel.exitSelectionMode()
    }

    if (showDeleteConfirmation) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text(if (deleteMode == "all") "Clear All Saved" else "Delete Selected") },
            text = {
                Text(
                    if (deleteMode == "all") "Are you sure you want to delete all saved articles?"
                    else "Are you sure you want to delete ${selectedArticles.size} selected articles?"
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (deleteMode == "all") {
                        viewModel.unsaveAll()
                    } else {
                        viewModel.deleteSelected()
                    }
                    showDeleteConfirmation = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (savedNews.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No saved articles")
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSelectionMode) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { viewModel.exitSelectionMode() }) {
                            Icon(Icons.Default.Close, contentDescription = "Cancel selection")
                        }
                        Text(
                            text = "${selectedArticles.size} Selected",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Row {
                        IconButton(onClick = { viewModel.selectAll() }) {
                            Icon(Icons.Default.SelectAll, contentDescription = "Select All")
                        }
                        IconButton(onClick = {
                            deleteMode = "selected"
                            showDeleteConfirmation = true
                        }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete Selected",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(savedNews, key = { it.id }) { article ->
                    val isSelected = selectedArticles.contains(article.id)
                    MyLatestNewsPage(
                        newsItem = article,
                        isSelected = isSelected,
                        isSelectionMode = isSelectionMode,
                        onNewsClick = { news ->
                            if (isSelectionMode) {
                                viewModel.toggleSelection(news.id)
                            } else {
                                navController.navigate(
                                    Route.MyDetailedArticleScreen(
                                        image = news.image,
                                        title = news.title,
                                        description = news.description,
                                        content = news.content,
                                        fromSaved = true
                                    )
                                )
                            }
                        },
                        onLongClick = { news ->
                            viewModel.toggleSelection(news.id)
                        }
                    )
                }
            }
        }
    }
}
