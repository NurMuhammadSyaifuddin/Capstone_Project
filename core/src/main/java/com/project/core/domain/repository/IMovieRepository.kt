package com.project.core.domain.repository

import com.project.core.data.source.Resource
import com.project.core.domain.model.Credit
import com.project.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getPopularMovie(type: String): Flow<Resource<List<Movie>>>
    fun getNowPlayingMovie(type: String): Flow<Resource<List<Movie>>>
    fun getUpComingMovie(type: String): Flow<Resource<List<Movie>>>
    fun getCastMovie(id: Int): Flow<List<Credit>>
    fun getDetailMovie(id: Int): Flow<Movie>
    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun setFavoriteMovie(movie: Movie)
    fun getMovieById(id: Int): Flow<Movie>
    fun getSearchMovie(title: String): Flow<List<Movie>>

}