package com.rach.swipestore.data.remote

import AddProductResponse
import com.rach.swipestore.domain.model.ResponseItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @GET("api/public/get")
    suspend fun getProducts(): Response<List<ResponseItem>>

    @Multipart
    @POST("api/public/add")
    suspend fun addProduct(
        @Part("product_name") productName: RequestBody,
        @Part("product_type") productType: RequestBody,
        @Part("price") price: RequestBody,
        @Part("tax") tax: RequestBody,
        @Part files: List<MultipartBody.Part>
    ): Response<AddProductResponse>
}