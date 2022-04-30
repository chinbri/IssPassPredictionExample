package com.isspass.domain.location

import com.isspass.data.network.location.GetIssPredLocationNetworkRepository
import com.isspass.data.model.ApiResponseModel
import com.isspass.data.model.IssLocationResponseModel
import com.isspass.data.network.numbers.NumbersDataNetworkRepository
import com.isspass.domain.model.UseCaseResponse
import com.isspass.domain.model.iss.IssLocationItemEntity
import com.isspass.domain.model.iss.IssPredictedDataEntity
import javax.inject.Inject

class GetIssPredLocationUseCaseImpl @Inject constructor(
    private val getIssPredLocationNetworkRepository: GetIssPredLocationNetworkRepository,
    private val numbersDataNetworkRepository: NumbersDataNetworkRepository
    ): GetIssPredLocationUseCase {

    override suspend fun invoke(request: GetIssPredLocationParam): UseCaseResponse<IssPredictedDataEntity> {

        val result = getIssPredLocationNetworkRepository.getIssLocationForCoordinates(
            request.latitude,
            request.longitude,
            request.altitude
        )

        when(result){

            is ApiResponseModel.SuccessResponse<IssLocationResponseModel> -> {

                val successResponse = result.value

                val issPredictedDataEntity = IssPredictedDataEntity(
                    successResponse.response?.map { issLocationItemModel ->

                        val numberFact = getFactForNumber(issLocationItemModel.duration)

                        IssLocationItemEntity(
                            issLocationItemModel.duration,
                            issLocationItemModel.risetime,
                            numberFact
                        )

                    } ?: emptyList()
                )

                return UseCaseResponse.Response(issPredictedDataEntity)
            }

            is ApiResponseModel.ApiErrorResponse -> return UseCaseResponse.Error(result.message ?: "Error")

            is ApiResponseModel.ExceptionErrorResponse -> {
                println(result.t.message)
                return UseCaseResponse.Error(result.message ?: "Error")
            }
        }

    }

    private suspend fun getFactForNumber(duration: Long): String {

        return when(val numberFactResult: ApiResponseModel<String> = numbersDataNetworkRepository.getFactForNumber(duration.toInt())){

            is ApiResponseModel.ExceptionErrorResponse -> {
                println(numberFactResult.t)
                ""
            }

            is ApiResponseModel.SuccessResponse<String> ->
                numberFactResult.value

            else -> ""

        }

    }

}
