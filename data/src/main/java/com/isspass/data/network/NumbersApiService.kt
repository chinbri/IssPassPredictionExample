package com.isspass.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersApiService {

    @GET("/{number}")
    suspend fun getFactForNumber(
        @Path("number") number: Int
    ): String

}