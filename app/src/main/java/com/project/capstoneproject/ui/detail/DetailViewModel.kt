package com.project.capstoneproject.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.project.core.domain.model.Movie
import com.project.core.domain.model.TvShow
import com.project.core.domain.usecase.movie.MovieUseCase
import com.project.core.domain.usecase.tvshow.TvShowUseCase

class DetailViewModel(private val movieUseCase: MovieUseCase, private val tvShowUseCase: TvShowUseCase): ViewModel() {
    fun getCreditMovie(id: Int) = movieUseCase.getCastMovie(id).asLiveData()
    fun getDetailMovie(id: Int) = movieUseCase.getDetailMovie(id).asLiveData()
    fun getCreditTvShow(id: Int) = tvShowUseCase.getCastTvShow(id).asLiveData()
    fun getDetailTvShow(id: Int) = tvShowUseCase.getDetailTvShow(id).asLiveData()
    suspend fun setFavoriteMovie(movie: Movie) = movieUseCase.setFavoriteMovie(movie)
    suspend fun setFavoriteTvShow(tvShow: TvShow) = tvShowUseCase.setFavoriteTvShow(tvShow)
    fun getMovieById(id: Int) = movieUseCase.getMovieById(id).asLiveData()
    fun getTvShowById(id: Int) = tvShowUseCase.getTvShowById(id).asLiveData()
}