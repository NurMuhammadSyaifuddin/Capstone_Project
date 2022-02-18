package com.project.core.data.source.repository

import com.project.core.data.source.NetworkBoundService
import com.project.core.data.source.Resource
import com.project.core.data.source.local.TvShowLocalDataSource
import com.project.core.data.source.remote.TvShowRemoteDataSource
import com.project.core.data.source.remote.network.ApiResponse
import com.project.core.data.source.remote.response.tvshow.TvShowResponse
import com.project.core.domain.model.Credit
import com.project.core.domain.model.TvShow
import com.project.core.domain.repository.ITvShowRepository
import com.project.core.utils.DataMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class TvShowRepository(
    private val localdataSource: TvShowLocalDataSource,
    private val remoteDataSource: TvShowRemoteDataSource
): ITvShowRepository {

    override fun getPopularTvShow(type: String): Flow<Resource<List<TvShow>>> =
        object : NetworkBoundService<List<TvShow>, List<TvShowResponse>>(){
            override suspend fun saveCallResult(data: List<TvShowResponse>) =
                localdataSource.insertTvShow(DataMapper.mapResponseToEntitiesPopularTvShow(data))

            override suspend fun createCall(): Flow<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getPopularTvShow()

            override fun shoulFetch(data: List<TvShow>): Boolean =
                data.isNullOrEmpty()

            override fun loadFromDb(): Flow<List<TvShow>> =
                localdataSource.getAllTvShow(type).map { DataMapper.mapEntitiesToDomainPopularTvShow(it) }

        }.asFlow()

    override fun getTvShowById(id: Int): Flow<TvShow> =
        localdataSource.getTvShowById(id).map { DataMapper.mapEntityToDomainTvShow(it) }

    override fun getAiringTodayTvShow(type: String): Flow<Resource<List<TvShow>>> =
        object : NetworkBoundService<List<TvShow>, List<TvShowResponse>>(){
            override suspend fun saveCallResult(data: List<TvShowResponse>) =
                localdataSource.insertTvShow(DataMapper.mapResponseToEntitiesAiringTodayTvShow(data))

            override suspend fun createCall(): Flow<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getAiringTodayTvShow()

            override fun shoulFetch(data: List<TvShow>): Boolean =
                data.isNullOrEmpty()

            override fun loadFromDb(): Flow<List<TvShow>> =
                localdataSource.getAllTvShow(type).map { DataMapper.mapEntitiesToDomainAiringTodayTvShow(it) }

        }.asFlow()

    override fun getTopRatedTvShow(type: String): Flow<Resource<List<TvShow>>> =
        object : NetworkBoundService<List<TvShow>, List<TvShowResponse>>(){
            override suspend fun saveCallResult(data: List<TvShowResponse>) =
                localdataSource.insertTvShow(DataMapper.mapResponseToEntitiesTopRateTvShow(data))

            override suspend fun createCall(): Flow<ApiResponse<List<TvShowResponse>>> =
                remoteDataSource.getTopRatedTvShow()

            override fun shoulFetch(data: List<TvShow>): Boolean =
                data.isNullOrEmpty()

            override fun loadFromDb(): Flow<List<TvShow>> =
                localdataSource.getAllTvShow(type).map { DataMapper.mapEntitiesToDomainTopRateTvShow(it) }

        }.asFlow()

    override fun getCastTvShow(id: Int): Flow<List<Credit>> =
        remoteDataSource.getCreditTvShow(id).map { DataMapper.mapResponseToDomainCreditTvShow(it) }

    override fun getDetailTvShow(id: Int): Flow<TvShow> =
        remoteDataSource.getDetailTvShow(id).map { DataMapper.mapResponseToDomainTvShow(it) }

    override fun getFavoriteTvShow(): Flow<List<TvShow>> =
        localdataSource.getFavoriteTvShow().map { DataMapper.mapEntitiesToDomainTvShow(it) }

    override suspend fun setFavoriteTvShow(tvShow: TvShow) =
        localdataSource.setFavoriteTvShow(DataMapper.mapDomainToEntityTvShow(tvShow))

    override fun getSearchTvShow(name: String): Flow<List<TvShow>> =
        localdataSource.getSearchTvShow(name).map { DataMapper.mapEntitiesToDomainTvShow(it) }
}