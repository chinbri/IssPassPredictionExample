package com.isspass.data.network.location

import com.isspass.data.model.ApiResponseModel
import com.isspass.data.model.IssLocationResponseModel
import com.isspass.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class GetIssPredLocationNetworkRepositoryImpl @Inject constructor(private val apiService: ApiService):
    GetIssPredLocationNetworkRepository {

    val registerCount = 10

    override suspend fun getIssLocationForCoordinates(
        latitude: Long,
        longitude: Long,
        altitude: Long
    ): ApiResponseModel<IssLocationResponseModel> = with(Dispatchers.IO){

        val response: IssLocationResponseModel
        try {
            response = apiService.getIssPositionsFromCoordinates(latitude, longitude, altitude, registerCount)
        } catch (t: Throwable) {
            return ApiResponseModel.ExceptionErrorResponse(t.message, t)
        }
        if (response.message == "success") {
            return ApiResponseModel.SuccessResponse(response)
        }else{
            return ApiResponseModel.ApiErrorResponse(response.reason)
        }

    }

}