package com.mornhouse.numbersinfoapp.data.models

import com.mornhouse.numbersinfoapp.data.data_source.local.NumberEntity
import com.mornhouse.numbersinfoapp.domain.models.NumberInfo

data class NumberInfoResponse(
    val text: String,
    val number: Long,
    val found: Boolean,
    val type: String
)

fun NumberInfoResponse.toDomainModel(): NumberInfo {
    return NumberInfo(
        number = number,
        description = text
    )
}

fun NumberInfo.toEntityModel(): NumberEntity {
    return NumberEntity(
        number = number,
        description = description
    )
}
