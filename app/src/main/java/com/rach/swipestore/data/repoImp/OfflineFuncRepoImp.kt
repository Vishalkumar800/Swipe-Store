package com.rach.swipestore.data.repoImp

import ProductDetails
import android.util.Log
import com.rach.swipestore.data.ProductWithId
import com.rach.swipestore.data.local.AppDatabase
import com.rach.swipestore.data.local.Entity
import com.rach.swipestore.data.toEntity
import com.rach.swipestore.data.toProductWithId
import com.rach.swipestore.domain.repository.OfflineFuncRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFuncRepoImp @Inject constructor(
    private val appDatabase: AppDatabase
) : OfflineFuncRepo {

    override suspend fun addInRoom(productDetails: ProductDetails) {
        Log.d("tom2","entity ${productDetails.toEntity()}")
        appDatabase.dao().insert(productDetails.toEntity())
    }

    override fun getllPending(): Flow<List<ProductWithId>> {
        return appDatabase.dao().getAllInsert().map { list ->
            list.map { it.toProductWithId() }
        }
    }



    override suspend fun deleteComplete(productWithId: ProductWithId) {
        val entity = Entity(
            id = productWithId.id,
            productName = productWithId.productDetails.productName,
            productType = productWithId.productDetails.productType,
            price = productWithId.productDetails.price,
            tax = productWithId.productDetails.tax,
            files = productWithId.productDetails.files
        )
        appDatabase.dao().delete(entity)
    }
}
