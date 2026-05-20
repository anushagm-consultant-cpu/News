package com.example.newspulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newspulse.ui.screens.ExploreScreen
import com.example.newspulse.ui.screens.ProfileScreen
import com.example.newspulse.ui.screens.SaveScreen
import com.example.newspulse.ui.screens.homeScreenUI
import com.example.newspulse.navigation.Route
import com.example.newspulse.ui.components.MyNavBar
import com.example.newspulse.ui.components.MyTopBar
import com.example.newspulse.ui.screens.MyAppThemeScreen
import com.example.newspulse.ui.screens.MyDetailedArticleScreen
import com.example.newspulse.ui.screens.MyHelpScreen
import com.example.newspulse.ui.screens.MyInterestAndPreference
import com.example.newspulse.ui.screens.MyrNotificationScreen
import com.example.newspulse.ui.screens.MyreadingHistroy
import com.example.newspulse.ui.theme.NewsPulseTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsPulseTheme {
                MainScreen()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreen() {
    //  Initialize the NavController
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    //only showing the root tabs
    val rootRoutes =listOf(Route.Home::class, Route.Explore::class, Route.Save::class, Route.Profile::class)

    val showBottomBar = rootRoutes.any{currentDestination?.hasRoute(it)==true}



    //  Scaffold to layout the Bottom Bar
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopBar(onSearchClick = {})
        },
        bottomBar = {
           if(showBottomBar){
               MyNavBar(navController = navController)
           }
        }
    ) { innerPadding ->


            NavHost( //it is the empty window where the screens will appear
                navController = navController,
                startDestination = Route.Home, // Starting screen class
                modifier = Modifier.padding(innerPadding)
            ) {
                // Map each Route class to a Composable screen
                composable<Route.Home> {
                    homeScreenUI(navController=navController)
                }
                composable<Route.Explore> {
                    ExploreScreen()
                }
                composable<Route.Save> {
                    SaveScreen()
                }
                composable<Route.Profile> {
                    ProfileScreen(onNavigate = {route ->
                        navController.navigate(route)
                    })
                }
               composable<Route.MyreadingHistroy>{
                   MyreadingHistroy()
               }
                composable<Route.MyInterestAndPreference>{
                    MyInterestAndPreference()
                }
                composable<Route.MyrNotificationScreen>{
                    MyrNotificationScreen()
                }
                composable<Route.MyAppThemeScreen>{
                    MyAppThemeScreen()
                }
                composable<Route.MyHelpScreen> {
                    MyHelpScreen()
                }
                composable<Route.MyDetailedArticleScreen> {backStackEntry->

                    val args =backStackEntry.toRoute<Route.MyDetailedArticleScreen>()

                    MyDetailedArticleScreen(
                        image = args.image,
                        title = args.title,
                        description = args.description,
                        content = args.content
                    )
                }
            }
        }
    }
