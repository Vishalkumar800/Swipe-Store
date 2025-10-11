package com.rach.swipestore.presentation.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.rach.swipestore.presentation.theme.SwipeStoreTheme
import com.rach.swipestore.presentation.viewModel.FieldStateViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen2() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // 🔹 States
    var productType by remember { mutableStateOf("") }
    var productName by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var tax by remember { mutableStateOf("") }
    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    var errorMessage by remember { mutableStateOf("") }

    val productTypes = listOf("Electronics", "Clothing", "Groceries", "Accessories", "Home Decor")

    // 🔹 Image Picker Launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        // Allow only up to 3 images
        imageUris = uris.take(3)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Product", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        containerColor = Color(0xFFF5F6FA)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 🔹 Card Container
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    // 🔹 Product Type Dropdown
                    Text("Select Product Type", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Spacer(Modifier.height(6.dp))

                    var expanded by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        TextField(
                            value = productType,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Choose Type") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            productTypes.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type) },
                                    onClick = {
                                        productType = type
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    // 🔹 Product Name
                    OutlinedTextField(
                        value = productName,
                        onValueChange = { productName = it },
                        label = { Text("Product Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    // 🔹 Price
                    OutlinedTextField(
                        value = price,
                        onValueChange = { price = it },
                        label = { Text("Selling Price (₹)") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    // 🔹 Tax
                    OutlinedTextField(
                        value = tax,
                        onValueChange = { tax = it },
                        label = { Text("Tax Rate (%)") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    // 🔹 Image Picker
                    Text("Product Images (Optional)", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Select Images")
                    }

                    Spacer(Modifier.height(8.dp))

                    // 🔹 Image Previews
                    AnimatedVisibility(visible = imageUris.isNotEmpty()) {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            items(imageUris) { uri ->
                                Image(
                                    painter = rememberAsyncImagePainter(uri),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .border(
                                            1.dp,
                                            Color.Gray.copy(alpha = 0.3f),
                                            RoundedCornerShape(12.dp)
                                        ),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    // 🔹 Submit Button
                    Button(
                        onClick = {
                            when {
                                productType.isBlank() -> errorMessage = "Please select a product type"
                                productName.isBlank() -> errorMessage = "Product name cannot be empty"
                                price.toDoubleOrNull() == null -> errorMessage = "Enter a valid price"
                                tax.toDoubleOrNull() == null -> errorMessage = "Enter a valid tax rate"
                                else -> {
                                    errorMessage = ""
                                    scope.launch {
                                        // TODO: Call API to add product
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Add Product", fontSize = 17.sp)
                    }

                    // 🔹 Validation Message
                    AnimatedVisibility(visible = errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

private val productTypesList = listOf("Electronics", "Clothing", "Groceries", "Accessories", "Home Decor")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    fieldViewModel: FieldStateViewModel = hiltViewModel()
) {

    val scrollState = rememberScrollState()
    var dropDownMenuExpanded by remember { mutableStateOf(false) }
    val productType by fieldViewModel.productTypeField.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(10.dp)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

           Column (modifier = Modifier.padding(16.dp)){

               Text("Select Product Type", fontWeight = FontWeight.SemiBold , fontSize = 16.sp)
               Spacer(modifier = Modifier.height(6.dp))

               ExposedDropdownMenuBox(
                   expanded = dropDownMenuExpanded,
                   onExpandedChange = {dropDownMenuExpanded = !dropDownMenuExpanded}
               ) {
                   TextField(
                       value = productType,
                       onValueChange = {},
                       modifier = Modifier.menuAnchor().fillMaxWidth(),
                       readOnly = true,
                       label = {Text("Choose Type")},
                       trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropDownMenuExpanded)},
                       colors = ExposedDropdownMenuDefaults.textFieldColors()
                   )

                   ExposedDropdownMenu(
                       expanded = dropDownMenuExpanded,
                       onDismissRequest = {dropDownMenuExpanded = false}
                   ) {
                       productTypesList.forEach { item ->
                           DropdownMenuItem(
                               text = {Text(item)},
                               onClick = {
                                   fieldViewModel.onProductTypeFieldChange(item)
                                   dropDownMenuExpanded = false
                               }
                           )
                       }
                   }
               }
           }

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
