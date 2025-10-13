package com.rach.swipestore.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.rach.swipestore.common.Resources
import com.rach.swipestore.presentation.components.CustomEmptyScreen
import com.rach.swipestore.presentation.components.CustomProgressBar
import com.rach.swipestore.presentation.components.ErrorScreen
import com.rach.swipestore.presentation.viewModel.MainViewModel


@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val searchData by viewModel.searchData.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search products...") }
        )

        when (val data = searchData) {
            is Resources.Loading -> {
                CustomProgressBar()
            }

            is Resources.Error -> {
                ErrorScreen(errorMessage = data.errorMessage ?: "")
            }

            is Resources.Empty -> {
                CustomProgressBar()
            }

            is Resources.Success -> {
                if (data.data?.isEmpty() == true){

                    CustomEmptyScreen()

                }else{
                    HomeScreenItemScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        productList = data.data ?: emptyList()
                    )
                }
            }
        }
    }
}
