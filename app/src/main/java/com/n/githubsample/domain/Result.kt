package com.n.githubsample.domain

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val message: String) : Result<T>()
    object Loading : Result<Nothing>()
}
