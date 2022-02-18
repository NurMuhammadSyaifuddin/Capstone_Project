package com.project.core.data.source.local

import com.project.core.data.source.local.entitiy.MovieEntiity
import com.project.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSource(private val movieDao: MovieDao) {

    fun getAllMovies(type: String): Flow<List<MovieEntiity>> = movieDao.getAllMovies(type)

    fun getMovieById(id: Int): Flow<MovieEntiity> = movieDao.getMovieById(id)

    fun getSearchMovie(title: String): Flow<List<MovieEntiity>> = movieDao.getSearchMovie(title)

    suspend fun insertMovies(movies: List<MovieEntiity>) = movieDao.insertMovies(movies)

    fun getFavoriteMovies(): Flow<List<MovieEntiity>> = movieDao.getFavoriteMovies()

    suspend fun setFavoriteMovie(movie: MovieEntiity){
        movie.isFavorite = !movie.isFavorite
        movieDao.updateMovie(movie)
    }
}