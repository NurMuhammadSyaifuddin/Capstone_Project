package com.project.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class TvShow(
    val id: Int,
    val name: String?,
    val overview: String?,
    val isFavorite: Boolean = false,
    val type: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: String?,
    val firstAirDate: String?,
    val genres: @RawValue List<Genre>? = emptyList()
): Parcelable
