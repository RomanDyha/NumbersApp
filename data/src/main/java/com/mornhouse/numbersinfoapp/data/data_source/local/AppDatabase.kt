package com.mornhouse.numbersinfoapp.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
// Create numbers database
@Database(entities = [NumberEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun numbersDao(): NumbersDao

}