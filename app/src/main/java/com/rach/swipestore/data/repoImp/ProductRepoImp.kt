package com.rach.swipestore.data.repoImp

import com.rach.swipestore.common.Resources
import com.rach.swipestore.data.remote.ApiService
import com.rach.swipestore.domain.model.AddProductResponse
import com.rach.swipestore.domain.model.ProductDetails
import com.rach.swipestore.domain.model.ResponseItem
import com.rach.swipestore.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepoImp @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {
    override suspend fun getProducts(): Flow<Resources<List<ResponseItem>>> = flow {
        emit(Resources.Loading())
        try {
            val response = apiService.getProducts()
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(Resources.Success(data = data))
                }
            } else {
                emit(Resources.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resources.Error(e.localizedMessage ?: "Unknown Error Found"))
        }
    }

    override suspend fun addProduct(productDetails: ProductDetails): Flow<Resources<AddProductResponse>> =
        flow {
            emit(Resources.Loading())
            try {
                val response = apiService.addProduct(
                    request = productDetails
                )
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        emit(Resources.Success(data = data))
                    }
                } else {
                    emit(Resources.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(Resources.Error(e.localizedMessage ?: "Unknown Error Found"))
            }
        }
}