package com.mornhouse.numbersinfoapp.data.data_source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mornhouse.numbersinfoapp.domain.models.NumberInfo

// table to store information about number
@Entity(tableName = "number")
data class NumberEntity(
    @PrimaryKey
    val number: Long,
    val description: String,
)

// convert NumberEntity to domain model
fun NumberEntity.toDomainModel(): NumberInfo {
   return NumberInfo(
        number = number,
        description = description
    )
}


fun List<NumberEntity>.toDomainModel(): List<NumberInfo> {
    return map {
        NumberInfo(
            number = it.number,
            description = it.description
        )
    }
}








