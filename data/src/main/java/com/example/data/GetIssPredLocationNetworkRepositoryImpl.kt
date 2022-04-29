package com.example.data

import javax.inject.Inject

class GetIssPredLocationNetworkRepositoryImpl @Inject constructor(): GetIssPredLocationNetworkRepository {

    override suspend fun test(): String {
        return "test repository"
    }

}