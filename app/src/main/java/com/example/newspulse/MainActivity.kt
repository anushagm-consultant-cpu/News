package com.example.newspulse

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
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
                        MainScreen(themeViewModel = themeViewModel, fontViewModel = fontViewModel)
                    }
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun MainScreen(
        viewModel: HomeViewModel = hiltViewModel(),
        themeViewModel: ThemeViewModel = hiltViewModel(),
        fontViewModel: FontViewModel = hiltViewModel()
    ) {

        val authViewModel: AuthViewModel = hiltViewModel()
        val savedViewModel: SavedViewModel = hiltViewModel()
        val navController = rememberNavController()

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

        // Auth logic
        val auth = FirebaseAuth.getInstance()
        val startDestination = if (auth.currentUser != null) Route.Home else Route.Onboarding

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
                authViewModel = authViewModel
            )
        }
    }
}
