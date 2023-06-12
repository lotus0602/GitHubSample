package com.n.githubsample.domain

sealed class Result<out T : Any> {
    data class Success<T : Any>(val data: T) : Result<T>()
    data class Error<T : Any>(val message: String) : Result<T>()
}