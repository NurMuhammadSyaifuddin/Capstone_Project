package com.project.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.core.domain.usecase.movie.MovieUseCase
import com.project.core.domain.usecase.tvshow.TvShowUseCase

class FavoriteViewModel(private val movieUseCase: MovieUseCase, private val tvShowUseCase: TvShowUseCase): ViewModel() {
    fun getFavoriteMovies() = movieUseCase.getFavoriteMovies().asLiveData()
    fun getFavoriteTvShow() = tvShowUseCase.getFavoriteTvShow().asLiveData()
}