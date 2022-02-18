package com.project.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Movie(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val popularity: Float?,
    val isFavorite: Boolean,
    val genres: @RawValue List<Genre>? = emptyList(),
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: String?,
    val releaseDate: String?,
    val type: String?
): Parcelable