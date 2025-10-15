import android.net.Uri

data class ProductDetails(
    val productName: String?,
    val productType: String?,
    val price: String?,
    val tax: String?,
    val files: List<Uri>?
)

data class AddProductResponse(
    val message: String,
    val productId: Int,
    val productDetails: ProductDetails,
    val success: Boolean
)
