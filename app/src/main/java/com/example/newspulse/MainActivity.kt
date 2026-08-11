package com.example.newspulse

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newspulse.navigation.AppNavGraph
import com.example.newspulse.navigation.Route
import com.example.newspulse.ui.components.homeComponents.MyNavBar
import com.example.newspulse.ui.components.homeComponents.MyTopBar
import com.example.newspulse.ui.theme.NewsPulseTheme
import com.example.newspulse.ui.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.platform.LocalContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var intentData by mutableStateOf<Intent?>(null)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        intentData=intent
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val appTheme by themeViewModel.appTheme.collectAsState()
            val fontViewModel: FontViewModel = hiltViewModel()

            CompositionLocalProvider(LocalFontScale provides fontViewModel.fontScale) {
                NewsPulseTheme(appTheme = appTheme) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreen(themeViewModel = themeViewModel,
                            fontViewModel = fontViewModel,
                            currentIntent = intentData,
                            onIntentHandled = {intentData = null})
                    }
                }
            }
        }
    }
    
    override fun onNewIntent(intent: Intent){
        super.onNewIntent(intent)
        setIntent(intent)
        intentData=intent
        
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun MainScreen(
        viewModel: HomeViewModel = hiltViewModel(),
        themeViewModel: ThemeViewModel = hiltViewModel(),
        fontViewModel: FontViewModel = hiltViewModel(),
        currentIntent: Intent?,
        onIntentHandled: () -> Unit
    ) {

        val authViewModel: AuthViewModel = hiltViewModel()
        val savedViewModel: SavedViewModel = hiltViewModel()
        val exploreViewModel: ExploreViewModel = hiltViewModel()
        val navController = rememberNavController()
        val context = LocalContext.current

        LaunchedEffect(currentIntent) {

            val articleTitle = currentIntent?.getStringExtra("title")

            if(!articleTitle.isNullOrEmpty()){
                navController.navigate(
                    Route.MyDetailedArticleScreen(
                        image = currentIntent.getStringExtra("image"),
                        title = articleTitle,
                        description = currentIntent.getStringExtra("description"),
                        content = currentIntent.getStringExtra("content"),
                        fromSaved = false
                    )
                )

               onIntentHandled()
            }
        }

        // Notification Permission Logic
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            Log.d("Permission", if (isGranted) "Granted" else "Denied")
        }

        LaunchedEffect(Unit) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
            authViewModel.notificationFcmToken()
        }



        // Set start destination to Splash to show the video first
        val startDestination = Route.Splash

        // Navigation UI logic
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val rootRoutes = listOf(Route.Home::class, Route.Explore::class, Route.Save::class, Route.Profile::class)
        val showBottomBar = rootRoutes.any { currentDestination?.hasRoute(it) == true }
        val isHome = currentDestination?.hasRoute(Route.Home::class) == true

        val topBarTitle = when {
            isHome -> "NewsPulse"
            currentDestination?.hasRoute(Route.Explore::class) == true -> "Explore"
            currentDestination?.hasRoute(Route.Save::class) == true -> "Saved"
            currentDestination?.hasRoute(Route.Profile::class) == true -> "Profile"
            else -> "NewsPulse"
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                if (showBottomBar) {
                    MyTopBar(title = topBarTitle, isCenter = isHome, onSearchClick = {})
                }
            },
            bottomBar = {
                if (showBottomBar) {
                    MyNavBar(navController = navController)
                }
            }
        ) { innerPadding ->
            AppNavGraph(
                navController = navController,
                startDestination = startDestination,
                innerPadding = innerPadding,
                homeViewModel = viewModel,
                savedViewModel = savedViewModel,
                themeViewModel = themeViewModel,
                fontViewModel = fontViewModel,
                authViewModel = authViewModel,
                exploreviewModel = exploreViewModel
            )
        }
    }
}
