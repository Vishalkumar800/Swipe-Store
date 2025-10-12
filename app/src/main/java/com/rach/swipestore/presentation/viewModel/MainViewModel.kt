package com.rach.swipestore.presentation.viewModel

import AddProductResponse
import ProductDetails
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rach.swipestore.common.Resources
import com.rach.swipestore.domain.model.ResponseItem
import com.rach.swipestore.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ProductRepository
): ViewModel() {

    private val _allProducts = MutableStateFlow<Resources<List<ResponseItem>>>(Resources.Loading())
    val allProducts : StateFlow<Resources<List<ResponseItem>>> = _allProducts.asStateFlow()

    private val _productAddedResponse = MutableStateFlow<Resources<AddProductResponse>>(Resources.Empty())
    val productAddedResponse : StateFlow<Resources<AddProductResponse>> = _productAddedResponse.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery : StateFlow<String> = _searchQuery.asStateFlow()

    init {
        getProducts()
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    fun getProducts(){
        viewModelScope.launch {

            _searchQuery.debounce(400)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    repository.getProducts(query.ifBlank { null })
                }.collect {
                    _allProducts.value = it
                }

            repository.getProducts().collect { data ->
                _allProducts.value = data
            }
        }
    }

    fun updateSearchQuery(query: String){
        _searchQuery.value = query
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