package com.rach.swipestore.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.rach.swipestore.R
import com.rach.swipestore.domain.model.ResponseItem
import com.rach.swipestore.presentation.theme.SwipeStoreTheme
import com.rach.swipestore.presentation.theme.balooFontFamily

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: ResponseItem,
    onClick: () -> Unit = {}
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            //  Product Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            ) {
                SubcomposeAsyncImage(
                    model = product.image,
                    contentDescription = "Product Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    loading = { CustomProgressBar() },
                    error = {
                        Image(
                            painter = painterResource(R.drawable.outline_broken_image_24),
                            contentDescription = "Error Image",
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                    }
                )

                // Gradient overlay (for text visibility)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xAA000000))
                            )
                        )
                )

                //  Price text on image
                Text(
                    text = "₹${product.price}",
                    color = Color.White,
                    fontSize = 17.sp,
                    style = TextStyle(
                        fontFamily = balooFontFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                )
            }

            //  Product Info
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = product.productName,
                    style = TextStyle(
                        fontFamily = balooFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Tax: ${product.tax}%",
                    style = TextStyle(
                        fontFamily = balooFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray,
                        fontSize = 12.sp
                    ),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
    val dummyProduct = ResponseItem(
        productName = "Nike Air Max Shoes",
        image = "https://images.unsplash.com/photo-1606813902915-9b98c4b74a3b",
        price = 4999.0,
        tax = 18.0,
        productType = "Footwear"
    )

    SwipeStoreTheme {
        ProductItem(
            product = dummyProduct
        )
    }

}
