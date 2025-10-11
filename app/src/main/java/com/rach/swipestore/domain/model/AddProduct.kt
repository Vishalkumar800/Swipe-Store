package com.rach.swipestore.domain.model

import com.google.gson.annotations.SerializedName

data class AddProductResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("product_details")
    val productDetails: ProductDetails,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("success")
    val success: Boolean
)

data class ProductDetails(
    @SerializedName("product_name")
    val productName: String?,
    @SerializedName("product_type")
    val productType: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("tax")
    val tax: String?,
    @SerializedName("files")
    val files: List<String>? // API ke response me agar file URLs ya names milte hain
)
