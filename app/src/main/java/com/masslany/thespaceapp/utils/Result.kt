package com.masslany.thespaceapp.utils

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error<U>(val exception: Exception) : Result<Nothing>()
}