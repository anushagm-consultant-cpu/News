package com.example.newspulse.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newspulse.navigation.Route

@Composable
fun MyNavBar(navController: NavHostController) {

    val navItems = listOf(
        NavItem("Home", Icons.Filled.Home,Icons.Outlined.Home, Route.Home),
        NavItem("Explore", Icons.Filled.SavedSearch,Icons.Outlined.Search, Route.Explore),
        NavItem("Save", Icons.Filled.Bookmark,Icons.Outlined.BookmarkBorder, Route.Save),
        NavItem("Profile", Icons.Filled.Person,Icons.Outlined.Person, Route.Profile),
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentDestination = navBackStackEntry?.destination

        navItems.forEach { item ->

            val isSelected = currentDestination?.hasRoute(item.route::class) == true

            NavigationBarItem(
                selected = isSelected,
                label = { Text(item.title) },

                onClick = {
                    navController.navigate(item.route) {

                        // Pop up to the start destination of the graph to avoid stacking
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // this  Avoid multiple copies of the same destination
                        launchSingleTop = true
                        // Restore state when re-selecting a previous item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if(isSelected){
                            item.iconSelected
                        }else{
                            item.iconUnselected
                        },
                        contentDescription = item.title,
                        modifier = Modifier.size(25.dp)

                    )
                }
            )
        }
    }
}

data class NavItem(
    val title: String,
    val iconSelected: ImageVector,
    val iconUnselected: ImageVector,
    val route: Route
)