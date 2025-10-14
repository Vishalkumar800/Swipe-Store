package com.rach.swipestore.data.repoImp

import AddProductResponse
import ProductDetails
import android.content.Context
import android.net.Uri
import androidx.compose.ui.geometry.Rect
import com.rach.swipestore.common.Resources
import com.rach.swipestore.data.remote.ApiService
import com.rach.swipestore.domain.model.ResponseItem
import com.rach.swipestore.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class ProductRepoImp @Inject constructor(
    private val apiService: ApiService,
    private val context: Context
) : ProductRepository {

    override fun getProducts(searchKeyword: String?): Flow<Resources<List<ResponseItem>>> = flow {
        emit(Resources.Loading())
        try {
            val response = apiService.getProducts()

            if (response.isSuccessful) {
                val body = response.body() ?: emptyList()

                val filterList = if (!searchKeyword.isNullOrBlank()) {

                    body.filter { items ->
                        items.productName.contains(searchKeyword, ignoreCase = true) ||
                                items.productType.contains(searchKeyword, ignoreCase = true)
                    }

                } else {
                    body
                }

                emit(Resources.Success(filterList))

            } else {
                emit(Resources.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resources.Error(e.localizedMessage ?: "Unknown Error Found"))
        }
    }

    override fun addProduct(productDetails: ProductDetails): Flow<Resources<AddProductResponse>> =
        flow {
            emit(Resources.Loading())
            try {
                val productNameBody =
                    productDetails.productName?.toRequestBody("text/plain".toMediaType())
                        ?: "".toRequestBody("text/plain".toMediaType())
                val productTypeBody =
                    productDetails.productType?.toRequestBody("text/plain".toMediaType())
                        ?: "".toRequestBody("text/plain".toMediaType())
                val priceBody = productDetails.price?.toRequestBody("text/plain".toMediaType())
                    ?: "".toRequestBody("text/plain".toMediaType())
                val taxBody = productDetails.tax?.toRequestBody("text/plain".toMediaType())
                    ?: "".toRequestBody("text/plain".toMediaType())

                val imagesParts = productDetails.files?.mapNotNull { uri ->
                    try {
                        prepareImagePart(context, uri, "files[]")
                    } catch (e: Exception) {

                        null
                    }
                } ?: emptyList()

                val response = apiService.addProduct(
                    productNameBody,
                    productTypeBody,
                    priceBody,
                    taxBody,
                    imagesParts
                )

                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        emit(Resources.Success(data))
                    } ?: emit(Resources.Error("Empty Response from server"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    emit(Resources.Error(errorBody ?: response.message()))
                }


            } catch (e: Exception) {
                emit(Resources.Error(e.localizedMessage ?: "Unknown Error Found"))
            }
        }


    private fun prepareImagePart(context: Context, uri: Uri, partName: String): MultipartBody.Part {
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"
        val extension = when {
            mimeType.contains("png") -> ".png"
            else -> ".jpg"
        }
        val tempFile = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}$extension")
        inputStream.use { input ->
            tempFile.outputStream().use { output -> input.copyTo(output) }
        }
        val requestFile = tempFile.asRequestBody(mimeType.toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData(partName, tempFile.name, requestFile)
        return part
    }
}