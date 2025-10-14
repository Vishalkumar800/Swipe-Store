package com.rach.swipestore.presentation.viewModel

import AddProductResponse
import ProductDetails
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rach.swipestore.common.Resources
import com.rach.swipestore.data.ProductWithId
import com.rach.swipestore.data.repoImp.OfflineFuncRepoImp
import com.rach.swipestore.domain.connectivity.ConnectivityObserver
import com.rach.swipestore.domain.model.ResponseItem
import com.rach.swipestore.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class RoomDataBaseState {
    object Loading : RoomDataBaseState()
    object Empty : RoomDataBaseState()
    data class Success(val data: List<ProductWithId>) : RoomDataBaseState()
    data class Error(val errorMessage: String) : RoomDataBaseState()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val offlineRepository: OfflineFuncRepoImp,
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    private val _searchData = MutableStateFlow<Resources<List<ResponseItem>>>(Resources.Loading())
    val searchData: StateFlow<Resources<List<ResponseItem>>> = _searchData.asStateFlow()

    /**
     * Check Internet Connnection Code
     */
    val isConnected = connectivityObserver.isConnected.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L), false
    )


    private val _productAddedResponse =
        MutableStateFlow<Resources<AddProductResponse>>(Resources.Empty())
    val productAddedResponse: StateFlow<Resources<AddProductResponse>> =
        _productAddedResponse.asStateFlow()
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var searchJob: Job? = null

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }


    init {
        getProducts()

    }

    /**
     * Room Logic and InternetConnectionLogic
     */

    private val _pendingItems = MutableStateFlow<RoomDataBaseState>(RoomDataBaseState.Loading)
    val pendingItems: StateFlow<RoomDataBaseState> = _pendingItems.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun getProducts() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _searchQuery
                .debounce(400)
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    repository.getProducts(query)
                }
                .collect { data ->
                    _searchData.value = data
                }
        }


    }

    fun addProduct(internetState: Boolean, productDetails: ProductDetails) {
        viewModelScope.launch {
            if (internetState) {
                repository.addProduct(
                    productDetails = productDetails
                ).collect { response ->
                    _productAddedResponse.value = response
                }
            } else {
                _productAddedResponse.value = Resources.Loading()
                try {
                    delay(1000)
                    offlineRepository.addInRoom(productDetails)
                    _productAddedResponse.value = Resources.Success(
                        data = AddProductResponse(
                            message = "Saved Locally! Will upload when online.",
                            productDetails = productDetails,
                            productId = 0,
                            success = true
                        )
                    )
                } catch (e: Exception) {
                    _productAddedResponse.value =
                        Resources.Error(e.localizedMessage ?: "Failed to save offline")
                }
            }
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _searchData.value = Resources.Empty()
    }

    fun getPendingItems() {
        _pendingItems.value = RoomDataBaseState.Loading
        viewModelScope.launch {
            try {
                val data = offlineRepository.getllPending().first()
                _pendingItems.value = if (data.isEmpty()) {
                    RoomDataBaseState.Empty
                } else {
                    RoomDataBaseState.Success(data = data)
                }
            } catch (e: Exception) {
                _pendingItems.value = RoomDataBaseState.Error(
                    e.localizedMessage ?: "Something Went Wrong"
                )
            }
        }
    }


}