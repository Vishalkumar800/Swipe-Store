package com.rach.swipestore.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rach.swipestore.common.Resources
import com.rach.swipestore.domain.model.AddProductResponse
import com.rach.swipestore.domain.model.ProductDetails
import com.rach.swipestore.domain.model.ResponseItem
import com.rach.swipestore.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private val _allProducts = MutableStateFlow<Resources<List<ResponseItem>>>(Resources.Loading())
    val allProducts : StateFlow<Resources<List<ResponseItem>>> = _allProducts.asStateFlow()

    private val _productAddedResponse = MutableStateFlow<Resources<AddProductResponse>>(Resources.Loading())
    val productAddedResponse : StateFlow<Resources<AddProductResponse>> = _productAddedResponse.asStateFlow()

    init {
        getProducts()
    }

    fun getProducts(){
        viewModelScope.launch {
            repository.getProducts().collect { data ->
                _allProducts.value = data
            }
        }
    }

    fun addProduct(productDetails: ProductDetails){
        viewModelScope.launch {
            repository.addProduct(
                productDetails = productDetails
            ).collect {response ->
                _productAddedResponse.value = response
            }
        }
    }

}