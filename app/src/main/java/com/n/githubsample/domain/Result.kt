package com.n.githubsample.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.Exception

sealed class Result<out T : Any> {

    data class Success<T : Any>(val data: T) : Result<T>()

    sealed class Fail : Result<Nothing>() {
        abstract val exception: Exception

        fun errorType(): String = when (this) {
            is ResponseError -> errorType
            else -> ""
        }

        open fun errorMessage(): String = exception.message ?: ""

        data class Error(override val exception: Exception) : Fail()

        data class NetworkError(override val exception: Exception) : Fail()

        data class ResponseError(
            val errorType: String,
            val errorMessage: String,
            override val exception: Exception = Exception()
        ) : Fail() {
            override fun errorMessage(): String = errorMessage
        }
    }
}

@Serializable
abstract class FailResponse(
    val error: String = "",
    @SerialName("error_description")
    val errorDescription: String = "",
    @SerialName("error_uri")
    val errorUri: String = ""
) {
    fun isSuccess(): Boolean = error.isEmpty()
}