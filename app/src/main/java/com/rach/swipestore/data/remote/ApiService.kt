package com.rach.swipestore.data.remote

import com.rach.swipestore.domain.model.AddProductResponse
import com.rach.swipestore.domain.model.ProductDetails
import com.rach.swipestore.domain.model.ResponseItem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("api/public/get")
    suspend fun getProducts(): Response<List<ResponseItem>>

    @POST("api/public/add")
    suspend fun addProduct(
        @Body request : ProductDetails
    ): Response<AddProductResponse>
}