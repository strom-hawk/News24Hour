package com.example.domain.data

sealed class Response<T>(val data: T? = null, val errorMessage: String? = null) {
    class Loading<T> : Response<T>()
    class Error<T>(errorMessage: String) : Response<T>(errorMessage = errorMessage)
    class Success<T>(data: T? = null) : Response<T>(data = data)
}