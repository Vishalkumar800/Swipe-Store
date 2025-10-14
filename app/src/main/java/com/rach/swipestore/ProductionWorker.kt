package com.rach.swipestore

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.rach.swipestore.domain.repository.OfflineFuncRepo
import com.rach.swipestore.domain.repository.ProductRepository
import kotlinx.coroutines.flow.first

class ProductionWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: ProductRepository,
    private val offlineFuncRepo: OfflineFuncRepo
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            val pendingMessages = offlineFuncRepo.getllPending().first()
            if (pendingMessages.isEmpty()) return Result.success()

            for (item in pendingMessages) {
                try {
                    repository.addProduct(item.productDetails).collect { response ->
                        Log.d("SyncWorker5", "Sent ${item.productDetails.productName} → $response")
                    }
                    offlineFuncRepo.deleteComplete(item)
                } catch (e: Exception) {
                    Log.e("SyncWorker5", "Failed ${item.productDetails.productName}: ${e.message}")
                }
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }


}


class ProductionWorkerFactory(
    private val productRepository: ProductRepository,
    private val offlineFuncRepo: OfflineFuncRepo
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            ProductionWorker::class.java.name -> {
                ProductionWorker(
                    context = appContext,
                    params = workerParameters,
                    repository = productRepository,
                    offlineFuncRepo = offlineFuncRepo
                )
            }

            else -> {
                null
            }
        }
    }
}