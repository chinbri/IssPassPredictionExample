package com.isspass.data.network.numbers

import com.isspass.data.model.ApiResponseModel
import com.isspass.data.model.IssLocationResponseModel
import com.isspass.data.network.NumbersApiService
import javax.inject.Inject

class NumbersDataNetworkRepositoryImpl @Inject constructor(val apiService: NumbersApiService):
    NumbersDataNetworkRepository {

    override suspend fun getFactForNumber(number: Int): ApiResponseModel<String> {

        val response: String

        return try {
            response = apiService.getFactForNumber(number)
            ApiResponseModel.SuccessResponse(response)
        } catch (t: Throwable) {
            ApiResponseModel.ExceptionErrorResponse(t.message, t)
        }

    }

}