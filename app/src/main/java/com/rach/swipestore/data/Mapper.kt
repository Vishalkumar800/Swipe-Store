package com.rach.swipestore.data

import ProductDetails
import com.rach.swipestore.data.local.Entity

fun ProductDetails.toEntity(): Entity {
    return Entity(
        productName = productName,
        productType = productType,
        price = price,
        tax = tax,
        files = files
    )
}

fun Entity.toProductDetails(): ProductDetails {
    return ProductDetails(
        productName = productName,
        productType = productType,
        price = price,
        tax = tax,
        files = files
    )
}

data class ProductWithId(
    val id: Int,
    val productDetails: ProductDetails
)


fun Entity.toProductWithId(): ProductWithId {
    return ProductWithId(
        id = id,
        productDetails = ProductDetails(
            productName = productName,
            productType = productType,
            price = price,
            tax = tax,
            files = files
        )
    )
}
