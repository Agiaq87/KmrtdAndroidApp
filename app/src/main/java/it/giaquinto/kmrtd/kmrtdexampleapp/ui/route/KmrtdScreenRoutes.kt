package it.giaquinto.kmrtd.kmrtdexampleapp.ui.route

sealed class KmrtdScreenRoutes(val route: String) {
    object HomeScreen : KmrtdScreenRoutes("HomeScreen")
    object ResultScreen : KmrtdScreenRoutes("ResultScreen")
}