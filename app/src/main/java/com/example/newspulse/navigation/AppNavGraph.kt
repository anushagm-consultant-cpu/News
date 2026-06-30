package com.example.newspulse.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.newspulse.ui.components.detailArticle.ArticleSwipeScreen
import com.example.newspulse.ui.components.onBoarding.CreateAccountScreen
import com.example.newspulse.ui.components.onBoarding.OnboardingScreen
import com.example.newspulse.ui.components.onBoarding.loginScreen
import com.example.newspulse.ui.components.profileoptionsScreens.DemoFrontSizeScreen
import com.example.newspulse.ui.components.profileoptionsScreens.MyAppThemeScreen
import com.example.newspulse.ui.components.profileoptionsScreens.MyHelpScreen
import com.example.newspulse.ui.components.profileoptionsScreens.MyInterestAndPreference
import com.example.newspulse.ui.components.profileoptionsScreens.MyrNotificationScreen
import com.example.newspulse.ui.components.profileoptionsScreens.MyreadingHistroy
import com.example.newspulse.ui.screens.ExploreScreen
import com.example.newspulse.ui.screens.ProfileScreen
import com.example.newspulse.ui.screens.SaveScreen
import com.example.newspulse.ui.screens.homeScreenUI
import com.example.newspulse.ui.viewmodel.AuthViewModel
import com.example.newspulse.ui.viewmodel.FontViewModel
import com.example.newspulse.ui.viewmodel.HomeViewModel
import com.example.newspulse.ui.viewmodel.SavedViewModel
import com.example.newspulse.ui.viewmodel.ThemeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Any,
    innerPadding: PaddingValues,
    homeViewModel: HomeViewModel,
    savedViewModel: SavedViewModel,
    themeViewModel: ThemeViewModel,
    fontViewModel: FontViewModel,
    authViewModel: AuthViewModel,


    ){
    val allNews by homeViewModel.allNews.collectAsState()

    NavHost( //it is the empty window where the screens will appear
        navController = navController,
        startDestination = startDestination, // Starting screen class
        modifier = Modifier.fillMaxSize()
    ) {
        // Map each Route class to a Composable screen
        composable<Route.Onboarding> {
            OnboardingScreen(
                onSkipClick = {
                    navController.navigate(Route.Home) {
                        popUpTo(Route.Onboarding::class) {
                            inclusive = true
                        }

                    }

                },
                onLoginClick = {
                    navController.navigate(Route.Login)
                }
            )

        }

        //login screen
        composable<Route.Login> {
            loginScreen(
                onCreateAccountClick = {
                    navController.navigate(Route.CreateAccount)

                },
                onLoginSuccess = {
                    navController.navigate(Route.Home) {
                        // Clear the backstack so user can't "go back" to login
                        popUpTo(Route.Login::class) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        //create account screen
        composable<Route.CreateAccount> {
            CreateAccountScreen(
                onLoginClick = {
                    navController.navigate(Route.Login)
                },
                onSignUpSuccess = {
                    navController.navigate(Route.Home){
                        popUpTo(Route.CreateAccount::class){
                            inclusive=true
                        }
                    }
                }
            )
        }

        composable<Route.Home> {
            homeScreenUI(
                navController = navController,
                innerPadding = innerPadding,
                homeViewModel = homeViewModel,
            )
        }
        composable<Route.Explore> {
            ExploreScreen(innerPadding)
        }
        composable<Route.Save> {
            SaveScreen(
                navController = navController,
                innerPadding = innerPadding,
                viewModel = savedViewModel
            )
        }
        composable<Route.Profile> {
            val authViewModel: AuthViewModel = viewModel()
            ProfileScreen(onNavigate = { route ->
                navController.navigate(route)
            },
                onLogout = {

                    authViewModel.logout {
                        navController.navigate(Route.Onboarding) {
                            popUpTo(0) //clears the entire backstack
                            {
                                inclusive = true
                            }
                        }
                    }
                },
                innerPadding)
        }
        composable<Route.MyreadingHistroy> {
            MyreadingHistroy(
                onBackClick = { navController.popBackStack() },
                onNewsClick = { news ->
                    navController.navigate(
                        Route.MyDetailedArticleScreen(
                            image = news.image,
                            title = news.title,
                            description = news.description,
                            content = news.content,
                            fromSaved = false // Or true if you want swiping through history
                        )
                    )
                }
            )
        }
        composable<Route.MyInterestAndPreference> {
            MyInterestAndPreference(onBackClick = { navController.popBackStack() })
        }
        composable<Route.MyrNotificationScreen> {
            MyrNotificationScreen(onBackClick = { navController.popBackStack() })
        }
        composable<Route.MyAppThemeScreen> {
            MyAppThemeScreen(
                themeViewModel = themeViewModel,
                onBackClick = { navController.popBackStack() })
        }
        composable<Route.FontSizeScreen> {
            DemoFrontSizeScreen(
                onBackClick = { navController.popBackStack() },
                viewModel =fontViewModel
            )
        }
        composable<Route.MyHelpScreen> {
            MyHelpScreen(onBackClick = { navController.popBackStack() })
        }
        composable<Route.MyDetailedArticleScreen> { backStackEntry ->

            val args = backStackEntry.toRoute<Route.MyDetailedArticleScreen>()


            val savedNews by savedViewModel.savedNews.collectAsState()

            // Choose article list based for swiping saved news or all news
            val articleList = if (args.fromSaved) {
                savedNews // Use only articles from the Database
            } else {
                allNews // Use all articles from the API
            }

            val initialPageIndex = articleList.indexOfFirst { it.title == args.title }

            ArticleSwipeScreen(
                article = articleList,
                initialpage = initialPageIndex.coerceAtLeast(0),
                onBack = { navController.popBackStack() },
            )
        }
    }


}