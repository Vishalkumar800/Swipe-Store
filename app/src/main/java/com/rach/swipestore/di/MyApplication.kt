package com.rach.swipestore.di

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.rach.swipestore.ProductionWorkerFactory
import com.rach.swipestore.common.BASE_URL
import com.rach.swipestore.data.local.AppDatabase
import com.rach.swipestore.data.remote.ApiService
import com.rach.swipestore.data.repoImp.OfflineFuncRepoImp
import com.rach.swipestore.data.repoImp.ProductRepoImp
import com.rach.swipestore.domain.repository.OfflineFuncRepo
import com.rach.swipestore.domain.repository.ProductRepository
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@HiltAndroidApp
class MyApplication : Application(), Configuration.Provider {
    private lateinit var customWorkerFactory: ProductionWorkerFactory

    override fun onCreate() {
        super.onCreate()

        // Dependencies initialize karen
        val apiService = createApiService()
        val appDatabase = createDatabase()
        val repository: ProductRepository = ProductRepoImp(apiService, this)
        val offlineFuncRepo: OfflineFuncRepo = OfflineFuncRepoImp(appDatabase)

        // Worker factory banayein
        customWorkerFactory = ProductionWorkerFactory(repository, offlineFuncRepo)

        // WorkManager initialize karen
        WorkManager.initialize(this, workManagerConfiguration)
    }

    private fun createApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    private fun createDatabase(): AppDatabase {
        return AppDatabase.invoke(this)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(customWorkerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
}
