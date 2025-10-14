package com.rach.swipestore.presentation.ui

import AddProductResponse
import ProductDetails
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.rach.swipestore.R
import com.rach.swipestore.common.Resources
import com.rach.swipestore.presentation.components.CustomOutlinedTextField
import com.rach.swipestore.presentation.components.CustomProgressBar
import com.rach.swipestore.presentation.theme.SwipeStoreTheme
import com.rach.swipestore.presentation.theme.balooFontFamily
import com.rach.swipestore.presentation.viewModel.FieldStateViewModel
import com.rach.swipestore.presentation.viewModel.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


private val productTypesList =
    listOf("Electronics", "Clothing", "Groceries", "Accessories", "Home Decor")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    fieldViewModel: FieldStateViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    var dropDownMenuExpanded by remember { mutableStateOf(false) }
    val productType by fieldViewModel.productTypeField.collectAsState()
    val productName by fieldViewModel.productName.collectAsState()
    val sellingPrice by fieldViewModel.sellingPrice.collectAsState()
    val taxRate by fieldViewModel.taxRate.collectAsState()
    var imageUri by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var errorMessage by remember { mutableStateOf("") }

    val network by mainViewModel.isConnected.collectAsStateWithLifecycle()
    //AddProduct Response By ViewModel
    val addProductResponse by mainViewModel.productAddedResponse.collectAsState()

    LaunchedEffect(addProductResponse) {
        when (addProductResponse) {
            is Resources.Success -> {
                val data = (addProductResponse as Resources.Success<AddProductResponse>).data
                data?.let {
                    if (it.success) {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        fieldViewModel.clearAllFields()
                        imageUri = emptyList()
                    } else {
                        Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            is Resources.Error -> {
                Toast.makeText(
                    context,
                    "Error ${(addProductResponse as Resources.Error).errorMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        imageUri = uris.take(3)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    "Select Product Type",
                    style = TextStyle(
                        fontFamily = balooFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                    )
                )
                Text("$network")
                Spacer(modifier = Modifier.height(6.dp))
                AnimatedVisibility(
                    visible = errorMessage.isNotEmpty()
                ) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }

                ExposedDropdownMenuBox(
                    expanded = dropDownMenuExpanded,
                    onExpandedChange = { dropDownMenuExpanded = !dropDownMenuExpanded }
                ) {
                    TextField(
                        value = productType,
                        onValueChange = {},
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        readOnly = true,
                        label = { Text("Choose Type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropDownMenuExpanded) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                    )

                    ExposedDropdownMenu(
                        expanded = dropDownMenuExpanded,
                        onDismissRequest = { dropDownMenuExpanded = false }
                    ) {
                        productTypesList.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    fieldViewModel.onProductTypeFieldChange(item)
                                    dropDownMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                CustomOutlinedTextField(
                    value = productName,
                    onValueChange = {
                        fieldViewModel.onProductNameChange(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Product Name",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))
                CustomOutlinedTextField(
                    value = sellingPrice,
                    onValueChange = {
                        fieldViewModel.onSellingPriceChange(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Selling Price",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))
                CustomOutlinedTextField(
                    value = taxRate,
                    onValueChange = {
                        fieldViewModel.onTaxRateChange(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = "Tax Rate (%)",
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    "Product Images (Optional)",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = {
                        imagePickerLauncher.launch("image/*")
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFD7549))
                ) {
                    Text(
                        "Select Images", style = TextStyle(
                            fontFamily = balooFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Spacer(Modifier.height(12.dp))

                AnimatedVisibility(
                    visible = imageUri.isNotEmpty()
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(imageUri) { uri ->
                            Image(
                                painter = rememberAsyncImagePainter(model = uri),
                                contentDescription = stringResource(R.string.selected_product_image),
                                modifier = Modifier
                                    .size(150.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .clip(RoundedCornerShape(16.dp))
                                    .border(
                                        1.dp,
                                        Color.Gray.copy(alpha = 0.3f),
                                        RoundedCornerShape(16.dp)
                                    ),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        when {
                            productType.isBlank() -> errorMessage = "Please select a product type"
                            productName.isBlank() -> errorMessage = "Product name cannot be empty"
                            sellingPrice.toDoubleOrNull() == null -> errorMessage =
                                "Enter a valid price"

                            taxRate.toDoubleOrNull() == null -> errorMessage =
                                "Enter a valid tax rate"

                            else -> {
                                errorMessage = ""
                                scope.launch {
                                    val productsDetails = ProductDetails(
                                        productName = productName,
                                        productType = productType,
                                        price = sellingPrice,
                                        tax = taxRate,
                                        files = imageUri
                                    )
                                    mainViewModel.addProduct(
                                        internetState = network,
                                        productDetails = productsDetails
                                    )
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFD7549)
                    )
                ) {
                    Text(
                        "Add Product", fontSize = 17.sp, style = TextStyle(
                            fontFamily = balooFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }

        if (addProductResponse is Resources.Loading) {
            CustomProgressBar()
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SwipeStoreTheme {
        AddProductScreen()
    }
}
