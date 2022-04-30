package com.isspass.data.network.numbers

import com.isspass.data.model.ApiResponseModel
import com.isspass.data.model.IssLocationResponseModel

interface NumbersDataNetworkRepository{

    suspend fun getFactForNumber(number: Int): ApiResponseModel<String>

}