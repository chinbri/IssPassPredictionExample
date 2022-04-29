package com.example.domain.iss

import com.example.data.GetIssPredLocationNetworkRepository
import javax.inject.Inject

class GetIssPredLocationUseCaseImpl @Inject constructor(val getIssPredLocationNetworkRepository: GetIssPredLocationNetworkRepository): GetIssPredLocationUseCase {

    override suspend fun invoke(request: String): String {
        return getIssPredLocationNetworkRepository.test()
    }

}