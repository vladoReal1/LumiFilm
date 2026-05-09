package com.example.lumifilm_semestralka.data.local


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LumiFilmDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}