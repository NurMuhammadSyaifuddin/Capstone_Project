package com.project.core.data.source.repository

import com.project.core.data.source.NetworkBoundService
import com.project.core.data.source.local.MovieLocalDataSource
import com.project.core.data.source.remote.MovieRemoteDataSource
import com.project.core.data.source.remote.network.ApiResponse
import com.project.core.data.source.remote.response.movie.MovieResponse
import com.project.core.domain.model.Movie
import com.project.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import com.project.core.data.source.Resource
import com.project.core.domain.model.Credit
import com.project.core.utils.DataMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class MovieRepository(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
): IMovieRepository {
    override fun getPopularMovie(type: String): Flow<Resource<List<Movie>>> =
        object : NetworkBoundService<List<Movie>, List<MovieResponse>>(){
            override suspend fun saveCallResult(data: List<MovieResponse>) =
                localDataSource.insertMovies(DataMapper.mapResponseToEntitiesPopularMovie(data))


            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getPopularMovies()

            override fun shoulFetch(data: List<Movie>): Boolean =
                data.isNullOrEmpty()

            override fun loadFromDb(): Flow<List<Movie>> =
                localDataSource.getAllMovies(type).map {
                    DataMapper.mapEntitiesToDomainMovie(it)
                }

        }.asFlow()

    override fun getNowPlayingMovie(type: String): Flow<Resource<List<Movie>>> =
        object : NetworkBoundService<List<Movie>, List<MovieResponse>>(){
            override suspend fun saveCallResult(data: List<MovieResponse>) =
                localDataSource.insertMovies(DataMapper.mapResponseToEntitiesNowPlayingMovie(data))

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getNowPlayingMovies()

            override fun shoulFetch(data: List<Movie>): Boolean =
                data.isNullOrEmpty()

            override fun loadFromDb(): Flow<List<Movie>> =
                localDataSource.getAllMovies(type).map { DataMapper.mapEntitiesToDomainMovie(it) }

        }.asFlow()

    override fun getUpComingMovie(type: String): Flow<Resource<List<Movie>>> =
        object : NetworkBoundService<List<Movie>, List<MovieResponse>>(){
            override suspend fun saveCallResult(data: List<MovieResponse>) =
                localDataSource.insertMovies(DataMapper.mapResponseToEntitiesUpComingMovie(data))

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getUpComingMovies()

            override fun shoulFetch(data: List<Movie>): Boolean =
                data.isNullOrEmpty()

            override fun loadFromDb(): Flow<List<Movie>> =
                localDataSource.getAllMovies(type).map { DataMapper.mapEntitiesToDomainMovie(it) }

        }.asFlow()

    override fun getCastMovie(id: Int): Flow<List<Credit>> =
        remoteDataSource.getCreditMovie(id).map { DataMapper.mapResponseToDomainCreditMovie(it) }

    override fun getDetailMovie(id: Int): Flow<Movie> =
        remoteDataSource.getDetailMovie(id).map { DataMapper.mapResponseToDomainMovies(it) }

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        localDataSource.getFavoriteMovies().map { DataMapper.mapEntitiesToDomainMovie(it) }

    override suspend fun setFavoriteMovie(movie: Movie) =
        localDataSource.setFavoriteMovie(DataMapper.mapDomainToEntityMovie(movie))

    override fun getMovieById(id: Int): Flow<Movie> =
        localDataSource.getMovieById(id).map { DataMapper.mapEntityToDomainMovie(it) }

    override fun getSearchMovie(title: String): Flow<List<Movie>> =
        localDataSource.getSearchMovie(title).map { DataMapper.mapEntitiesToDomainMovie(it) }
}