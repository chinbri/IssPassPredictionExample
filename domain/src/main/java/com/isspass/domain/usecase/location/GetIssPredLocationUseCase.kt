package com.isspass.domain.usecase.location

import com.isspass.domain.usecase.UseCase
import com.isspass.domain.model.UseCaseResponse
import com.isspass.domain.model.iss.IssPredictedDataEntity

interface GetIssPredLocationUseCase:
    UseCase<GetIssPredLocationParam, UseCaseResponse<IssPredictedDataEntity>>

data class GetIssPredLocationParam(val latitude: Long, val longitude: Long, val altitude: Long)