package com.project.core.utils

import com.project.core.data.source.local.entitiy.MovieEntiity
import com.project.core.data.source.local.entitiy.TvShowEntity
import com.project.core.data.source.remote.response.CreditResponse
import com.project.core.data.source.remote.response.movie.MovieResponse
import com.project.core.data.source.remote.response.tvshow.TvShowResponse
import com.project.core.domain.model.Credit
import com.project.core.domain.model.Genre
import com.project.core.domain.model.Movie
import com.project.core.domain.model.TvShow

object DataMapper {

    fun mapResponseToEntitiesPopularMovie(input: List<MovieResponse>): List<MovieEntiity> =
        input.map {
            MovieEntiity(
                it.id ?: 0,
                it.title,
                it.overview,
                false,
                it.popularity,
                "popular",
                it.posterPath,
                it.backdropPath,
                it.voteAverage.toString(),
                it.releaseDate
            )
        }

    fun mapResponseToEntitiesNowPlayingMovie(input: List<MovieResponse>): List<MovieEntiity> =
        input.map {
            MovieEntiity(
                it.id ?: 0,
                it.title,
                it.overview,
                false,
                it.popularity,
                "nowplaying",
                it.posterPath,
                it.backdropPath,
                it.voteAverage.toString(),
                it.releaseDate
            )
        }

    fun mapResponseToEntitiesUpComingMovie(input: List<MovieResponse>): List<MovieEntiity> =
        input.map {
            MovieEntiity(
                it.id ?: 0,
                it.title,
                it.overview,
                false,
                it.popularity,
                "upcoming",
                it.posterPath,
                it.backdropPath,
                it.voteAverage.toString(),
                it.releaseDate
            )
        }

    fun mapResponseToDomainMovies(input: MovieResponse): Movie =
            Movie(
                input.id,
                input.title,
                input.overview,
                input.popularity,
                false,
                input.genres?.map { genre ->
                    Genre(genre.id, genre.name) },
                input.posterPath,
                input.backdropPath,
                input.voteAverage.toString(),
                input.releaseDate,
                ""
            )

    fun mapEntitiesToDomainMovie(input: List<MovieEntiity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                popularity = it.popularity,
                isFavorite = it.isFavorite,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                voteAverage = it.voteAverenge,
                releaseDate = it.releaseDate,
                type = it.type
            )
        }

    fun mapEntitiesToDomainTvShow(input: List<TvShowEntity>): List<TvShow> =
        input.map{
            TvShow(
                it.id,
                it.name,
                it.overview,
                it.isFavorite,
                it.type,
                it.posterPath,
                it.backdropPath,
                it.voteAverage,
                it.firstAirDate
            )
        }

    fun mapResponseToDomainCreditMovie(input: List<CreditResponse>): List<Credit> =
        input.map {
            Credit(
                it.castId,
                it.character,
                it.name,
                it.profilePath.toString()
            )
        }

    fun mapResponseToEntitiesPopularTvShow(input: List<TvShowResponse>): List<TvShowEntity> =
        input.map {
            TvShowEntity(
                it.id as Int,
                it.name,
                it.overview,
                false,
                "popular",
                it.posterPath,
                it.backdropPath,
                it.voteAverage,
                it.firstAirDate,
            )
        }

    fun mapEntitiesToDomainPopularTvShow(input: List<TvShowEntity>): List<TvShow> =
        input.map {
            TvShow(
                it.id,
                it.name,
                it.overview,
                it.isFavorite,
                it.type,
                it.posterPath,
                it.backdropPath,
                it.voteAverage,
                it.firstAirDate
            )
        }

    fun mapResponseToEntitiesAiringTodayTvShow(input: List<TvShowResponse>): List<TvShowEntity> =
        input.map {
            TvShowEntity(
                it.id as Int,
                it.name,
                it.overview,
                false,
                "airingtoday",
                it.posterPath,
                it.backdropPath,
                it.voteAverage,
                it.firstAirDate
            )
        }

    fun mapEntitiesToDomainAiringTodayTvShow(input: List<TvShowEntity>): List<TvShow> =
        input.map {
            TvShow(
                it.id,
                it.name,
                it.overview,
                it.isFavorite,
                it.type,
                it.posterPath,
                it.backdropPath,
                it.voteAverage,
                it.firstAirDate
            )
        }

    fun mapResponseToEntitiesTopRateTvShow(input: List<TvShowResponse>): List<TvShowEntity> =
        input.map {
            TvShowEntity(
                it.id as Int,
                it.name,
                it.overview,
                false,
                "toprate",
                it.posterPath,
                it.backdropPath,
                it.voteAverage,
                it.firstAirDate
            )
        }

    fun mapEntitiesToDomainTopRateTvShow(input: List<TvShowEntity>): List<TvShow> =
        input.map {
            TvShow(
                it.id,
                it.name,
                it.overview,
                it.isFavorite,
                it.type,
                it.posterPath,
                it.backdropPath,
                it.voteAverage,
                it.firstAirDate
            )
        }

    fun mapResponseToDomainCreditTvShow(input: List<CreditResponse>): List<Credit> =
        input.map {
            Credit(
                it.castId,
                it.character,
                it.name,
                it.profilePath.toString()
            )
        }

    fun mapResponseToDomainTvShow(input: TvShowResponse): TvShow =
        TvShow(
            input.id as Int,
            input.name,
            input.overview,
            false,
            "",
            input.posterPath,
            input.backdropPath,
            input.voteAverage,
            input.firstAirDate,
            input.genres?.map { genre ->
                Genre(genre.id, genre.name)
            }
        )

    fun mapDomainToEntityMovie(input: Movie): MovieEntiity =
        MovieEntiity(
            input.id as Int,
            input.title,
            input.overview,
            input.isFavorite,
            input.popularity,
            input.type,
            input.posterPath,
            input.backdropPath,
            input.voteAverage,
            input.releaseDate
        )

    fun mapDomainToEntityTvShow(input: TvShow): TvShowEntity =
        TvShowEntity(
            input.id,
            input.name,
            input.overview,
            input.isFavorite,
            input.type,
            input.posterPath,
            input.backdropPath,
            input.voteAverage,
            input.firstAirDate
        )

    fun mapEntityToDomainMovie(input: MovieEntiity): Movie =
        Movie(
            id = input.id,
            title = input.title,
            overview = input.overview,
            popularity = input.popularity,
            isFavorite = input.isFavorite,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            voteAverage = input.voteAverenge,
            releaseDate = input.releaseDate,
            type = input.type
        )

    fun mapEntityToDomainTvShow(input: TvShowEntity): TvShow =
        TvShow(
            id = input.id,
            name = input.name,
            overview = input.overview,
            isFavorite = input.isFavorite,
            type = input.type,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            voteAverage = input.voteAverage,
            firstAirDate = input.firstAirDate
        )
}