package com.rach.swipestore.domain.repository

import AddProductResponse
import ProductDetails
import com.rach.swipestore.common.Resources
import com.rach.swipestore.domain.model.ResponseItem
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(searchKeyword: String? = null): Flow<Resources<List<ResponseItem>>>
    fun addProduct(productDetails: ProductDetails): Flow<Resources<AddProductResponse>>

}