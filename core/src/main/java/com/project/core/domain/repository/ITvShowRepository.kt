package com.project.core.domain.repository

import com.project.core.data.source.Resource
import com.project.core.domain.model.Credit
import com.project.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface ITvShowRepository {
    fun getPopularTvShow(type: String): Flow<Resource<List<TvShow>>>
    fun getTvShowById(id: Int): Flow<TvShow>
    fun getAiringTodayTvShow(type: String): Flow<Resource<List<TvShow>>>
    fun getTopRatedTvShow(type: String): Flow<Resource<List<TvShow>>>
    fun getCastTvShow(id: Int): Flow<List<Credit>>
    fun getDetailTvShow(id: Int): Flow<TvShow>
    fun getFavoriteTvShow(): Flow<List<TvShow>>
    suspend fun setFavoriteTvShow(tvShow: TvShow)
    fun getSearchTvShow(name: String): Flow<List<TvShow>>
}