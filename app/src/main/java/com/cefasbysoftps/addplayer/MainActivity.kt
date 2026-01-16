package com.cefasbysoftps.addplayer

import DownloaderViewModel
import HistoryScreen
import LoginScreen
import MainScreen
import PlayerScreen
import PlayerViewModel
import SplashScreen
import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cefasbysoftps.addplayer.ui.theme.AddPLayerTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            AddPLayerTheme {
//                val moviesDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES)
//                moviesDir?.mkdirs()

                val navController = rememberNavController()
                SetupNavGraph(navController)
            }

        }
    }
}

// -------------------- Navigation Graph --------------------
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(navController)
        }

        composable("login") {
            LoginScreen(navController)
        }

        composable("main") {
            MainScreen(navController)
        }

        composable("history") {
            HistoryScreen(navController)
        }
        composable("player") {
            PlayerScreen()
        }
    }
}