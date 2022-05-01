package com.isspass.domain.model

sealed class UseCaseResponse<T> {
    data class Response<T>(val value: T): UseCaseResponse<T>()
    data class Error<T>(val message: String): UseCaseResponse<T>()
}