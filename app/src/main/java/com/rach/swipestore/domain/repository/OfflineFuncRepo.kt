package com.rach.swipestore.domain.repository

import ProductDetails
import com.rach.swipestore.data.ProductWithId
import kotlinx.coroutines.flow.Flow

interface OfflineFuncRepo {

    suspend fun addInRoom(productDetails: ProductDetails)

    fun getllPending(): Flow<List<ProductWithId>>

    suspend fun deleteComplete(productWithId: ProductWithId)

}