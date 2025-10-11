package com.rach.swipestore.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PaymentScreen() {

    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
    ){
        Text("Payment Screen")
    }

}


@Composable
fun SettingsScreen() {

    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
    ){
        Text("Settings Screen")
    }

}