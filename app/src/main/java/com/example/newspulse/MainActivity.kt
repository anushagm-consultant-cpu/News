package com.example.newspulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newspulse.ui.screens.ExploreScreen
import com.example.newspulse.ui.screens.ProfileScreen
import com.example.newspulse.ui.screens.SaveScreen
import com.example.newspulse.ui.screens.homeScreenUI
import com.example.newspulse.navigation.Route
import com.example.newspulse.ui.components.MyNavBar
import com.example.newspulse.ui.components.MyTopBar
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

    //  Use Scaffold to layout the Bottom Bar
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopBar()
        },
        bottomBar = {
            // This is the component you created earlier
            MyNavBar(navController = navController)
        }
    ) { innerPadding ->


            //  Define the Navigation Graph
            NavHost(
                navController = navController,
                startDestination = Route.Home, // Starting screen class
                modifier = Modifier.padding(innerPadding)
            ) {
                // Map each Route class to a Composable screen
                composable<Route.Home> {
                    homeScreenUI()
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
            }
        }
    }
