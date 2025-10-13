package com.rach.swipestore.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rach.swipestore.presentation.ui.AddProductScreen
import com.rach.swipestore.presentation.ui.HomeScreen
import com.rach.swipestore.presentation.ui.PaymentScreen
import com.rach.swipestore.presentation.ui.PendingScreen
import com.rach.swipestore.presentation.ui.SearchScreen
import com.rach.swipestore.presentation.ui.SettingsScreen

@Composable
fun MyAppNav(modifier: Modifier = Modifier,navController: NavHostController) {

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ){
        composable(Screens.HomeScreen.route){
            HomeScreen()
        }

        composable(Screens.Pending.route){
            PendingScreen()
        }

        composable (Screens.SearchScreen.route){
            SearchScreen()
        }
        composable(Screens.Setting.route){
            SettingsScreen()
        }

        composable(Screens.AddProductScreen.route){
            AddProductScreen()
        }

        composable(Screens.Pending.route){
            PendingScreen()
        }

        composable(Screens.Payment.route) {
            PaymentScreen()
        }
    }

}