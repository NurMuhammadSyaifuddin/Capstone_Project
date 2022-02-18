package com.project.core.data.source.local.entitiy

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvshow")
data class TvShowEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "overview")
    var overview: String?,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,

    @ColumnInfo(name = "type")
    var type: String?,

    @ColumnInfo(name = "poster_path")
    var posterPath: String?,

    @ColumnInfo(name = "backgrop_path")
    var backdropPath: String?,

    @ColumnInfo(name = "vote_average")
    var voteAverage: String?,

    @ColumnInfo(name = "first_air_date")
    var firstAirDate: String?
)