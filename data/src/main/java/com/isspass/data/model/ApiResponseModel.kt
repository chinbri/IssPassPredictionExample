package com.isspass.data.model

sealed class ApiResponseModel<T> {

    data class SuccessResponse<T>(
        val value: T
    ): ApiResponseModel<T>()

    data class ApiErrorResponse<T>(
        val message: String?
    ): ApiResponseModel<T>()

    data class ExceptionErrorResponse<T>(
        val message: String?,
        val t: Throwable
    ): ApiResponseModel<T>()

}