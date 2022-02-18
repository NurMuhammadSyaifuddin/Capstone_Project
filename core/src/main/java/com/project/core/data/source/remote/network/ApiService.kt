package com.project.core.data.source.remote.network

import com.project.core.BuildConfig
import com.project.core.data.source.remote.response.BaseCreditResponse
import com.project.core.data.source.remote.response.BaseResponse
import com.project.core.data.source.remote.response.CreditResponse
import com.project.core.data.source.remote.response.movie.MovieResponse
import com.project.core.data.source.remote.response.tvshow.TvShowResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovie(@Query("api_key") apiKey: String = BuildConfig.DB_API_KEY): BaseResponse<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String = BuildConfig.DB_API_KEY): BaseResponse<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(@Query("api_key") apiKey: String = BuildConfig.DB_API_KEY): BaseResponse<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.DB_API_KEY
    ): MovieResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.DB_API_KEY
    ): BaseCreditResponse<CreditResponse>

    @GET("tv/popular")
    suspend fun getPopularTvShow(@Query("api_key") apiKey: String = BuildConfig.DB_API_KEY): BaseResponse<TvShowResponse>

    @GET("tv/airing_today")
    suspend fun getAiringTodayTvShow(@Query("api_key") apiKey: String = BuildConfig.DB_API_KEY): BaseResponse<TvShowResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShow(@Query("api_key") apiKey: String = BuildConfig.DB_API_KEY): BaseResponse<TvShowResponse>

    @GET("tv/{tv_id}")
    suspend fun getDetailTvShow(
        @Path("tv_id") tvshowId: Int,
        @Query("api_key") apiKey: String = BuildConfig.DB_API_KEY
    ): TvShowResponse

    @GET("tv/{tv_id}/credits")
    suspend fun getCreditsTvShow(
        @Path("tv_id") tvshowId: Int,
        @Query("api_key") apiKey: String = BuildConfig.DB_API_KEY
    ): BaseCreditResponse<CreditResponse>
}