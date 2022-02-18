package com.project.capstoneproject.ui.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.core.domain.usecase.tvshow.TvShowUseCase

class TvShowViewModel(private val tvShowUseCase: TvShowUseCase): ViewModel() {
    fun getPopularTvShow(type: String) = tvShowUseCase.getPopularTvShow(type).asLiveData()
    fun getAiringTodayTvShow(type: String) = tvShowUseCase.getAiringTodayTvShow(type).asLiveData()
    fun getTopRateTvShow(type: String) = tvShowUseCase.getTopRatedTvShow(type).asLiveData()
    fun getSearchTvShow(name: String) = tvShowUseCase.getSearchTvShow(name).asLiveData()
}