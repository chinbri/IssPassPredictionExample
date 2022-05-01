package com.isspass.domain.usecase.number

import com.isspass.data.model.ApiResponseModel
import com.isspass.data.network.numbers.NumbersDataNetworkRepository
import com.isspass.domain.model.UseCaseResponse
import javax.inject.Inject

class GetFactForNumberUseCaseImpl @Inject constructor(private val numbersDataNetworkRepository: NumbersDataNetworkRepository): GetFactForNumberUseCase {

    override suspend fun invoke(request: Int): UseCaseResponse<String> {

        when(val result = numbersDataNetworkRepository.getFactForNumber(request)){

            is ApiResponseModel.SuccessResponse<String> -> return UseCaseResponse.Response(result.value)

            is ApiResponseModel.ApiErrorResponse -> return UseCaseResponse.Error(result.message ?: "Error")

            is ApiResponseModel.ExceptionErrorResponse -> {
                println(result.t.message)
                return UseCaseResponse.Error(result.message ?: "Error")
            }
        }

    }

}