package com.rach.swipestore.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rach.swipestore.presentation.theme.balooFontFamily
import com.rach.swipestore.presentation.theme.redColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit
) {

    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                "Home Screen",
                style = TextStyle(
                    fontFamily = balooFontFamily,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 20.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = redColor.copy(alpha = 0.5f),
            titleContentColor = Color.White
        ),
        navigationIcon = {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "TopAppBar Navigation Icon",
                tint = Color.White
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search Icon",
                tint = Color.White
            )
        }
    )

}