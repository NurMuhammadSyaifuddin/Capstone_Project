package com.project.capstoneproject.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.core.domain.usecase.movie.MovieUseCase

class MovieViewModel(private val movieUseCase: MovieUseCase): ViewModel() {
    fun getPopularMovie(type: String) = movieUseCase.getPopularMovie(type).asLiveData()
    fun getNowPlayingMovie(type: String) = movieUseCase.getNowPlayingMovie(type).asLiveData()
    fun getUpComingMovie(type: String) = movieUseCase.getUpComingMovie(type).asLiveData()
    fun getSearchMovie(title: String) = movieUseCase.getSearchMovie(title).asLiveData()
}