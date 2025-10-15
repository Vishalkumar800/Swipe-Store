package com.rach.swipestore.di

import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.rach.swipestore.common.BASE_URL
import com.rach.swipestore.data.connectivity.AndroidConnectivityObserver
import com.rach.swipestore.data.local.AppDatabase
import com.rach.swipestore.data.remote.ApiService
import com.rach.swipestore.data.repoImp.OfflineFuncRepoImp
import com.rach.swipestore.data.repoImp.ProductRepoImp
import com.rach.swipestore.domain.connectivity.ConnectivityObserver
import com.rach.swipestore.domain.repository.OfflineFuncRepo
import com.rach.swipestore.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "room.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideProductRepository(
        apiService: ApiService,
        @ApplicationContext context: Context
    ): ProductRepository {
        return ProductRepoImp(apiService, context = context)
    }


    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext context: Context): ConnectivityObserver {
        return AndroidConnectivityObserver(context)
    }

    @Provides
    @Singleton
    fun provideOfflineRepo(appDatabase: AppDatabase): OfflineFuncRepo {
        return OfflineFuncRepoImp(appDatabase)
    }


}