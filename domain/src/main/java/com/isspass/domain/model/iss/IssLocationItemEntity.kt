package com.isspass.domain.model.iss

data class IssLocationItemEntity(
    val duration: Long,
    val riseTime: Long,
    val fact: String
)
val IssLocationItemEntity.durationMinutes: Long
    get() = duration / 60

val IssLocationItemEntity.durationSecondsWithoutMinutes: Long
    get() = duration % 60

