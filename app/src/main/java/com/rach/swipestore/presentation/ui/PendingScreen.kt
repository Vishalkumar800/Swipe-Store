package com.rach.swipestore.presentation.ui

import ProductDetails
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.rach.swipestore.R
import com.rach.swipestore.presentation.components.CustomEmptyScreen
import com.rach.swipestore.presentation.components.CustomProgressBar
import com.rach.swipestore.presentation.components.ErrorScreen
import com.rach.swipestore.presentation.viewModel.MainViewModel
import com.rach.swipestore.presentation.viewModel.RoomDataBaseState

@Composable
fun PendingScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val pendingItemsData by viewModel.pendingItems.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPendingItems()
    }

    when (val data = pendingItemsData) {
        is RoomDataBaseState.Loading -> {
            CustomProgressBar()
        }

        is RoomDataBaseState.Error -> {
            ErrorScreen(
                errorMessage = data.errorMessage
            )
        }

        is RoomDataBaseState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(data.data) { item ->
                    PendingScreenItem(
                        product = item.productDetails
                    )
                }
            }
        }

        is RoomDataBaseState.Empty -> {
            CustomEmptyScreen()
        }
    }
}

@Composable
fun PendingScreenItem(
    product: ProductDetails,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 🔹 Product image preview (first image only)
            if (!product.files.isNullOrEmpty()) {
                AsyncImage(
                    model = product.files.first(),
                    contentDescription = product.productName,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF8F8F8)),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Placeholder if no image
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFF8F8F8)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_broken_image_24),
                        contentDescription = "No Image",
                        tint = Color.Gray.copy(alpha = 0.6f),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // 🔹 Product details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.productName ?: "Unnamed Product",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Text(
                    text = product.productType ?: "Unknown Type",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "₹${product.price ?: "0"}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF4CAF50)
                    )
                    Text(
                        text = "+${product.tax ?: "0"}% tax",
                        fontSize = 13.sp,
                        color = Color(0xFF757575)
                    )
                }
            }

            // 🔹 Pending tag
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFFFC107).copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Pending",
                    color = Color(0xFFFFA000),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
