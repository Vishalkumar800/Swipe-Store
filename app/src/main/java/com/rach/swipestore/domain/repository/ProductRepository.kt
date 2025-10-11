package com.rach.swipestore.domain.repository

import com.rach.swipestore.common.Resources
import com.rach.swipestore.domain.model.AddProductResponse
import com.rach.swipestore.domain.model.ProductDetails
import com.rach.swipestore.domain.model.ResponseItem
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProducts(): Flow<Resources<List<ResponseItem>>>
    suspend fun addProduct(productDetails: ProductDetails): Flow<Resources<AddProductResponse>>

}