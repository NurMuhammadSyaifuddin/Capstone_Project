package com.project.capstoneproject.di

import com.project.capstoneproject.ui.detail.DetailViewModel
import com.project.capstoneproject.ui.movie.MovieViewModel
import com.project.capstoneproject.ui.tvshow.TvShowViewModel
import com.project.core.domain.usecase.movie.MovieInteractor
import com.project.core.domain.usecase.movie.MovieUseCase
import com.project.core.domain.usecase.tvshow.TvShowInteractor
import com.project.core.domain.usecase.tvshow.TvShowUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
    factory<TvShowUseCase> { TvShowInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { TvShowViewModel(get()) }
}