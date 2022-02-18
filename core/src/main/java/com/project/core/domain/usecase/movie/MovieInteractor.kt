package com.project.core.domain.usecase.movie

import com.project.core.data.source.Resource
import com.project.core.domain.model.Credit
import com.project.core.domain.model.Movie
import com.project.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository): MovieUseCase {
    override fun getPopularMovie(type: String): Flow<Resource<List<Movie>>> = movieRepository.getPopularMovie(type)
    override fun getNowPlayingMovie(type: String): Flow<Resource<List<Movie>>> = movieRepository.getNowPlayingMovie(type)
    override fun getUpComingMovie(type: String): Flow<Resource<List<Movie>>> = movieRepository.getUpComingMovie(type)
    override fun getCastMovie(id: Int): Flow<List<Credit>> = movieRepository.getCastMovie(id)
    override fun getDetailMovie(id: Int): Flow<Movie> = movieRepository.getDetailMovie(id)
    override fun getFavoriteMovies(): Flow<List<Movie>> = movieRepository.getFavoriteMovies()
    override suspend fun setFavoriteMovie(movie: Movie) = movieRepository.setFavoriteMovie(movie)
    override fun getMovieById(id: Int): Flow<Movie> = movieRepository.getMovieById(id)
    override fun getSearchMovie(title: String): Flow<List<Movie>> = movieRepository.getSearchMovie(title)
}