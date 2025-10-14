package com.rach.swipestore.presentation.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.rach.swipestore.R
import com.rach.swipestore.common.Resources
import com.rach.swipestore.domain.model.ResponseItem
import com.rach.swipestore.presentation.components.AdvanceBottomBar
import com.rach.swipestore.presentation.components.BottomAppBarDataClass
import com.rach.swipestore.presentation.components.CustomProgressBar
import com.rach.swipestore.presentation.components.CustomTopAppBar
import com.rach.swipestore.presentation.components.ErrorScreen
import com.rach.swipestore.presentation.components.ProductItem
import com.rach.swipestore.presentation.navigation.MyAppNav
import com.rach.swipestore.presentation.navigation.Screens
import com.rach.swipestore.presentation.viewModel.MainViewModel

@Composable
fun MyAppControl(viewModel: MainViewModel) {

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val route = currentBackStackEntry?.destination?.route
    val mainViewModel: MainViewModel

    val items = listOf(
        BottomAppBarDataClass(
            image = painterResource(R.drawable.outline_home_24),
            label = "Home",
            route = Screens.HomeScreen.route
        ),

        BottomAppBarDataClass(
            image = painterResource(R.drawable.outline_arrow_upload_progress_24),
            label = "Pending",
            route = Screens.Pending.route
        ),

        BottomAppBarDataClass(
            image = painterResource(R.drawable.outline_payments_24),
            label = "Payment",
            route = Screens.Payment.route
        ),

        BottomAppBarDataClass(
            image = painterResource(R.drawable.outline_settings_24),
            label = "Setting",
            route = Screens.Setting.route
        )
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars),
        bottomBar = {
            if (route != Screens.SearchScreen.route) {
                AdvanceBottomBar(
                    modifier = Modifier.fillMaxWidth(),
                    navController = navController,
                    items = items,
                    route = route,
                )
            }
        },
        topBar = {
            if (route != Screens.SearchScreen.route) {
                CustomTopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    onSearchClick = {
                        navController.navigate(Screens.SearchScreen.route)
                    },
                    onBackButtonClick = {
                        navController.navigateUp()
                    },
                    route = route,
                    onSearchBackButtonClick = {
                    },
                )
            }

        }
    ) { paddingValues ->

        MyAppNav(
            modifier = Modifier.padding(paddingValues),
            navController = navController
        )
    }

}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {

    val allProductState by viewModel.searchData.collectAsState()


    when (val state = allProductState) {
        is Resources.Loading -> {
            CustomProgressBar()
        }

        is Resources.Error -> {
            ErrorScreen(
                modifier = modifier,
                errorMessage = state.errorMessage ?: ""
            )
        }

        is Resources.Success -> {
            HomeScreenItemScreen(
                modifier = modifier
                    .fillMaxSize()
                    .padding(12.dp),
                productList = state.data ?: emptyList()
            )
        }

        is Resources.Empty -> {
            CustomProgressBar()
        }
    }


}


@Composable
fun HomeScreenItemScreen(modifier: Modifier = Modifier, productList: List<ResponseItem>) {

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(150.dp)
    ) {
        items(productList) { item ->
            ProductItem(
                product = item
            )
        }
    }

}

