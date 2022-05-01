package com.isspass.domain.usecase.number

import com.isspass.domain.model.UseCaseResponse
import com.isspass.domain.usecase.UseCase

interface GetFactForNumberUseCase: UseCase<Int, UseCaseResponse<String>>