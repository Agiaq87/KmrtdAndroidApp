package it.giaquinto.kmrtd.kmrtdexampleapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import it.giaquinto.kmrtd.kmrtdexampleapp.model.MRZInput
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.route.KmrtdScreenRoutes
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.screen.HomeScreen
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.screen.ResultScreen

@Composable
fun KmrtdNavGraph(
    padding: PaddingValues,
    showGithub : () -> Unit,
    showLinkedin : () -> Unit,
    jmrtd : (MRZInput) -> Unit,
    kmrtd : (MRZInput) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = KmrtdScreenRoutes.HomeScreen.route
    ) {
        composable(KmrtdScreenRoutes.HomeScreen.route) {
            HomeScreen(
                padding = padding,
                showGithub = showGithub,
                showLinkedin = showLinkedin,
                jmrtd = jmrtd,
                kmrtd = kmrtd,
            )
        }

        composable(KmrtdScreenRoutes.ResultScreen.route) {
            ResultScreen()
        }
    }
}