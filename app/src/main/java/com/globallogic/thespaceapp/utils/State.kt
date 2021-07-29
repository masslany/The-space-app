package com.globallogic.thespaceapp.utils

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Error(val throwable: Throwable) : State<Nothing>()
    data class Success<T>(val data: T) : State<T>()
}