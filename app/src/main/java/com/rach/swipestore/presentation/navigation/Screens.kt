package com.rach.swipestore.presentation.navigation

sealed class Screens(val route: String) {
    object HomeScreen : Screens("Home")
    object Pending: Screens("Pending")
    object Payment : Screens("Payment")
    object Setting : Screens("Setting")
    object AddProductScreen: Screens("AddProductScreen")
    object SearchScreen : Screens("Search Screen")
}