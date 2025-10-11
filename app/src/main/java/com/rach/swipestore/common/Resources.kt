package com.rach.swipestore.common

sealed class Resources<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {

    class Loading<T> : Resources<T>()
    class Success<T>(data: T? = null) : Resources<T>(data = data)
    class Error<T>(errorMessage: String) : Resources<T>(errorMessage = errorMessage)

}