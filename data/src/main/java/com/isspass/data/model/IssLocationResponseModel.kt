package com.isspass.data.model

data class IssLocationResponseModel(
    val message: String,
    val reason: String,
    val response: List<IssLocationItemModel>?
)


data class IssLocationItemModel(
    val duration: Long,
    val risetime: Long,
)


