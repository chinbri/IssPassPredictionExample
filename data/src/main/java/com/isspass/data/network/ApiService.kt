package com.isspass.data.network

import com.isspass.data.model.IssLocationResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("/iss/v1/")
    suspend fun getIssPositionsFromCoordinates(
        @Query("lat") latitude: Long,
        @Query("lon") longitude: Long,
        @Query("alt") altitude: Long,
    ): IssLocationResponseModel

}