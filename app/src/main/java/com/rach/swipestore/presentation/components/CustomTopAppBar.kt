package com.rach.swipestore.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rach.swipestore.R
import com.rach.swipestore.presentation.navigation.Screens
import com.rach.swipestore.presentation.theme.balooFontFamily
import com.rach.swipestore.presentation.theme.redColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit,
    route: String?,
    onBackButtonClick: () -> Unit,
    onSearchBackButtonClick: () -> Unit
) {

    TopAppBar(
        modifier = modifier,
        title = {
            if (route != null) {
                Text(
                    route,
                    style = TextStyle(
                        fontFamily = balooFontFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(start = 20.dp)
                )

            } else {
                Text(
                    "Home Screen",
                    style = TextStyle(
                        fontFamily = balooFontFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = redColor.copy(alpha = 0.5f),
            titleContentColor = Color.White
        ),
        navigationIcon = {
            if (route == Screens.HomeScreen.route) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "TopAppBar Navigation Icon",
                    tint = Color.White
                )
            } else if (route == Screens.SearchScreen.route) {
                IconButton(
                    onClick = onSearchBackButtonClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.arrow_back_icon)
                    )
                }
            } else {
                IconButton(
                    onClick = onBackButtonClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.arrow_back_icon)
                    )
                }
            }
        },
        actions = {
            if (route == Screens.HomeScreen.route) {
                IconButton(
                    onClick = {
                        onSearchClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            }
        }
    )

}