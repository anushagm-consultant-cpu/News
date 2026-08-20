package com.example.newspulse.navigation

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.example.newspulse.ui.listen.ListenScreen
import com.example.newspulse.ui.screens.ExploreScreen
import com.example.newspulse.ui.screens.ProfileScreen
import com.example.newspulse.ui.screens.SaveScreen
import com.example.newspulse.ui.screens.SplashScreen
import com.example.newspulse.ui.screens.homeScreenUI
import com.example.newspulse.ui.viewmodel.AuthViewModel
import com.example.newspulse.ui.viewmodel.ExploreViewModel
import com.example.newspulse.ui.viewmodel.FontViewModel
import com.example.newspulse.ui.viewmodel.HomeViewModel
import com.example.newspulse.ui.viewmodel.SavedViewModel
import com.example.newspulse.ui.viewmodel.ThemeViewModel
import com.example.newspulse.ui.viewmodel.TypographyViewModel
import com.google.firebase.auth.FirebaseAuth

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
    typographyViewModel: TypographyViewModel,
    authViewModel: AuthViewModel,
    exploreviewModel: ExploreViewModel
    ){
//    val allNews by homeViewModel.allNews.collectAsState()
//    val searchResults by homeViewModel.searchResults.collectAsState()

    val homeState by homeViewModel.state.collectAsState()
    val allNews = homeState.allNews
    val exploreState by exploreviewModel.state.collectAsState()
    val searchResults = exploreState.searchResults

    val context = LocalContext.current

    // Handle Notification Deep Link
    LaunchedEffect(Unit) {
        val intent = (context as? Activity)?.intent
        val articleTitle = intent?.getStringExtra("title")

        if (!articleTitle.isNullOrEmpty()) {
            Log.d("NotificationNav", "Navigating to: $articleTitle")
            navController.navigate(
                Route.MyDetailedArticleScreen(
                    image = intent.getStringExtra("image"),
                    title = articleTitle,
                    description = intent.getStringExtra("description"),
                    content = intent.getStringExtra("content"),
                    fromSaved = false
                )
            )
            // Clear the intent so it doesn't trigger again on rotation
            intent.removeExtra("title")
        }
    }


    NavHost( //it is the empty window where the screens will appear
        navController = navController,
        startDestination = startDestination, // Starting screen class
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Route.Splash> {
            SplashScreen(onVideoFinished = {
                val auth = FirebaseAuth.getInstance()
                val nextRoute = if (auth.currentUser != null) Route.Home else Route.Onboarding
                navController.navigate(nextRoute) {
                    popUpTo(Route.Splash) { inclusive = true }
                }
            })
        }

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
            ExploreScreen(
                navController = navController,
                innerPadding = innerPadding,
                exploreViewModel =exploreviewModel
            )
        }
        composable<Route.Save> {
            SaveScreen(
                navController = navController,
                innerPadding = innerPadding,
                viewModel = savedViewModel
            )
        }
        composable<Route.Profile> {
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
            MyInterestAndPreference(
                onBackClick = { navController.popBackStack() },
                exploreViewModel = exploreviewModel,
                typographyViewModel = typographyViewModel
            )
        }
        composable<Route.MyrNotificationScreen> {
            MyrNotificationScreen(onBackClick = { navController.popBackStack() })
        }
        composable<Route.Listen> {
            ListenScreen(
                onBackClick = { navController.popBackStack() },
                onNavigate = { route ->
                    navController.navigate(route)
                }
            )
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


            val articleList = when {
                args.fromSaved -> savedNews
                // If the article is found in search results, use that list
                searchResults.any { it.title.trim().equals(args.title.trim(), ignoreCase = true) } -> searchResults
                // Default to all news (Home screen list)
                else -> allNews
            }

            //  title matching: trim whitespace and ignore case
            val initialPageIndex = articleList.indexOfFirst {
                it.title.trim().equals(args.title.trim(), ignoreCase = true) 
            }
            
            Log.d("NotificationNav", "Matching title: '${args.title}' found at index: $initialPageIndex")

            ArticleSwipeScreen(
                article = articleList,
                initialpage = initialPageIndex.coerceAtLeast(0),
                onBack = { navController.popBackStack() },
            )
        }
    }
}
