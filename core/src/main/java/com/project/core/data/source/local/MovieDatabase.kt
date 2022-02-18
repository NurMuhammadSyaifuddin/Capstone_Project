package com.project.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.core.data.source.local.entitiy.MovieEntiity
import com.project.core.data.source.local.entitiy.TvShowEntity
import com.project.core.data.source.local.room.MovieDao
import com.project.core.data.source.local.room.TvShowDao

@Database(entities = [MovieEntiity::class, TvShowEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
}