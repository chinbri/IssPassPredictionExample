package com.isspass.data.network.location

import com.isspass.data.model.ApiResponseModel
import com.isspass.data.model.IssLocationResponseModel

interface GetIssPredLocationNetworkRepository{

    suspend fun getIssLocationForCoordinates(latitude: Long, longitude: Long, altitude: Long): ApiResponseModel<IssLocationResponseModel>

}