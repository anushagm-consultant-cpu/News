package com.example.newspulse

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.newspulse.ui.components.homeComponents.MyNavBar
import com.example.newspulse.ui.components.homeComponents.MyTopBar
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
import com.example.newspulse.ui.viewmodel.FontViewModel
import com.example.newspulse.ui.viewmodel.LocalFontScale
import com.example.newspulse.ui.theme.NewsPulseTheme
import com.example.newspulse.ui.viewmodel.HomeViewModel
import com.example.newspulse.ui.viewmodel.SavedViewModel
import androidx.compose.runtime.CompositionLocalProvider
import com.example.newspulse.ui.viewmodel.ThemeViewModel


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            //initializing themes
            val themeViewModel: ThemeViewModel = viewModel()
            val appTheme by themeViewModel.appTheme.collectAsState()
            val fontViewModel: FontViewModel = viewModel()

            CompositionLocalProvider(LocalFontScale provides fontViewModel.fontScale) {
                NewsPulseTheme(appTheme = appTheme) {// Everything inside here now reacts to the theme change
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreen(themeViewModel = themeViewModel,fontViewModel = fontViewModel)
                    }

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun MainScreen(
        viewModel: HomeViewModel = viewModel(),
        themeViewModel: ThemeViewModel = viewModel(),
        fontViewModel: FontViewModel = viewModel()
    ) {

        //firebase auth instance
        val auth=com.google.firebase.auth.FirebaseAuth.getInstance()
        val currentUser=auth.currentUser

        val startDestination = if (currentUser != null) Route.Home else Route.Onboarding

        val allNews by viewModel.allNews.collectAsState()

        //  Initialize the NavController
        val navController = rememberNavController()

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination


        //only showing the root tabs
        val rootRoutes =
            listOf(Route.Home::class, Route.Explore::class, Route.Save::class, Route.Profile::class)

        val showBottomBar = rootRoutes.any { currentDestination?.hasRoute(it) == true }

        val isHome = currentDestination?.hasRoute(Route.Home::class) == true

        val topBarTitle = when {
            isHome -> "NewsPulse"
            currentDestination?.hasRoute(Route.Explore::class) == true -> "Explore"
            currentDestination?.hasRoute(Route.Save::class) == true -> "Saved"
            currentDestination?.hasRoute(Route.Profile::class) == true -> "Profile"
            else -> "NewsPulse"
        }


        //  Scaffold to layout the Bottom Bar
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (showBottomBar) {
                    MyTopBar(
                        title = topBarTitle,
                        isCenter = isHome,
                        onSearchClick = {}
                    )
                }
            },
            bottomBar = {
                if (showBottomBar) {
                    MyNavBar(navController = navController)
                }
            }
        ) { innerPadding ->

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
                        innerPadding,
                        homeViewModel = viewModel
                    )
                }
                composable<Route.Explore> {
                    ExploreScreen(innerPadding)
                }
                composable<Route.Save> {
                    SaveScreen(
                        navController = navController,
                        innerPadding = innerPadding,
                        viewModel = viewModel()
                    )
                }
                composable<Route.Profile> {
                    ProfileScreen(onNavigate = { route ->
                        navController.navigate(route)
                    },
                        onLogout = {

                          auth.signOut()

                            navController.navigate(Route.Onboarding) {
                                popUpTo(0) //clears the entire backstack
                                 {
                                    inclusive = true
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

                    val savedViewModel: SavedViewModel = viewModel()
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
    }
}
