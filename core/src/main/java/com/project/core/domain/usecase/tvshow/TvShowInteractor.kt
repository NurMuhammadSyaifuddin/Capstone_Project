package com.project.core.domain.usecase.tvshow

import com.project.core.data.source.Resource
import com.project.core.domain.model.Credit
import com.project.core.domain.model.TvShow
import com.project.core.domain.repository.ITvShowRepository
import kotlinx.coroutines.flow.Flow

class TvShowInteractor(private val tvshowRepository: ITvShowRepository): TvShowUseCase {
    override fun getPopularTvShow(type: String): Flow<Resource<List<TvShow>>> = tvshowRepository.getPopularTvShow(type)
    override fun getTvShowById(id: Int): Flow<TvShow> = tvshowRepository.getTvShowById(id)
    override fun getAiringTodayTvShow(type: String): Flow<Resource<List<TvShow>>> = tvshowRepository.getAiringTodayTvShow(type)
    override fun getTopRatedTvShow(type: String): Flow<Resource<List<TvShow>>> = tvshowRepository.getTopRatedTvShow(type)
    override fun getCastTvShow(id: Int): Flow<List<Credit>> = tvshowRepository.getCastTvShow(id)
    override fun getDetailTvShow(id: Int): Flow<TvShow> = tvshowRepository.getDetailTvShow(id)
    override fun getFavoriteTvShow(): Flow<List<TvShow>> = tvshowRepository.getFavoriteTvShow()
    override suspend fun setFavoriteTvShow(tvShow: TvShow) = tvshowRepository.setFavoriteTvShow(tvShow)
    override fun getSearchTvShow(name: String): Flow<List<TvShow>> = tvshowRepository.getSearchTvShow(name)
}