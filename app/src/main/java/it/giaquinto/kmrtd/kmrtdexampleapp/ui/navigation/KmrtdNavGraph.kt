package it.giaquinto.kmrtd.kmrtdexampleapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import it.giaquinto.kmrtd.kmrtdexampleapp.model.MRZInput
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.route.KmrtdScreenRoutes
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.screen.ComparisonScreen
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.screen.HomeScreen
import it.giaquinto.kmrtd.kmrtdexampleapp.ui.viewmodel.SharedViewModel

@Composable
fun KmrtdNavGraph(
    padding: PaddingValues,
    showGithub : () -> Unit,
    showLinkedin : () -> Unit,
    readNfc: (MRZInput, Boolean) -> Unit,
    sharedViewModel: SharedViewModel,
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
                readJmrtd = readNfc,
                sharedViewModel = sharedViewModel
            ) {
                navController.navigate(KmrtdScreenRoutes.ResultScreen.route)
            }
        }

        composable(KmrtdScreenRoutes.ResultScreen.route) {
            sharedViewModel.kmrtdResultBuilder?.let { kmrtd ->
                sharedViewModel.jmrtdResultBuilder?.let { jmrtd ->
                    ComparisonScreen(
                        kmrtdResult = kmrtd,
                        jmrtdResult = jmrtd
                    )
                }
            }
        }
    }
}