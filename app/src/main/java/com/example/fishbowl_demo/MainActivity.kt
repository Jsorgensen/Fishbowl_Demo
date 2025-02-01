package com.example.fishbowl_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fishbowl_demo.data.model.Screen
import com.example.fishbowl_demo.ui.JokeScreen
import com.example.fishbowl_demo.ui.JokesScreen
import com.example.fishbowl_demo.ui.theme.Fishbowl_DemoTheme
import com.example.fishbowl_demo.util.JOKE_ID_KEY
import com.example.fishbowl_demo.viewmodel.JokeViewModel
import com.example.fishbowl_demo.viewmodel.JokesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {

    private var mNavController: NavHostController? = null
    val navController: NavHostController
        get() = mNavController ?: throw Exception("mNavController has not been set.")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            mNavController = rememberNavController()

            Fishbowl_DemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavHost(navController = navController)
                    }
                }
            }
        }
    }

    @Composable
    fun AppNavHost (navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = Screen.Jokes.route,
        ) {
            composable(Screen.Jokes.route) {
                val viewModel:JokesViewModel by viewModels()
                JokesScreen(
                    viewModel,
                    navController
                )
            }
            composable(
                route = "${Screen.Joke.route}/{$JOKE_ID_KEY}",
                arguments = listOf(navArgument(JOKE_ID_KEY) { type = NavType.IntType } ),
            ) { backStackEntry ->
                val jokeId = backStackEntry.arguments?.getInt(JOKE_ID_KEY, -1)
                    .let { when {
                        it == -1 -> return@let null
                        else -> it
                    } }
                val viewModel: JokeViewModel by viewModels()
                JokeScreen(
                    jokeId,
                    viewModel,
                    navController,
                )
            }
        }
    }
}