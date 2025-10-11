package com.rach.swipestore.domain.model


import com.google.gson.annotations.SerializedName

data class ResponseItem(
    @SerializedName("image")
    val image: String = "",
    @SerializedName("price")
    val price: Double = 0.0,
    @SerializedName("product_name")
    val productName: String = "",
    @SerializedName("product_type")
    val productType: String = "",
    @SerializedName("tax")
    val tax: Double = 0.0
)