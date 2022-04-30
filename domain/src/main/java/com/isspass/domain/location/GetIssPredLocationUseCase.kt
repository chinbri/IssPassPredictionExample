package com.isspass.domain.location

import com.isspass.data.model.ApiResponseModel
import com.isspass.domain.UseCase
import com.isspass.domain.model.UseCaseResponse
import com.isspass.domain.model.iss.IssPredictedDataEntity

interface GetIssPredLocationUseCase: UseCase<GetIssPredLocationParam, UseCaseResponse<IssPredictedDataEntity>>

data class GetIssPredLocationParam(val latitude: Long, val longitude: Long, val altitude: Long)