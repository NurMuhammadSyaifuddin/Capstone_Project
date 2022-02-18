package com.project.core.data.source.remote

import com.project.core.data.source.remote.network.ApiResponse
import com.project.core.data.source.remote.network.ApiService
import com.project.core.data.source.remote.response.CreditResponse
import com.project.core.data.source.remote.response.movie.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

@ExperimentalCoroutinesApi
class MovieRemoteDataSource(private val apiService: ApiService) {

    suspend fun getPopularMovies(): Flow<ApiResponse<List<MovieResponse>>> =
        channelFlow {
            try {
                val response = apiService.getPopularMovie()
                val data = response.results
                if (data.isNotEmpty()) {
                    send(ApiResponse.Success(data))
                } else {
                    send(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                send(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getNowPlayingMovies(): Flow<ApiResponse<List<MovieResponse>>> =
        channelFlow {
            try {
                val response = apiService.getNowPlayingMovies()
                val data = response.results
                if (data.isNotEmpty()) {
                    send(ApiResponse.Success(data))
                } else {
                    send(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                send(ApiResponse.Error(e.message.toString()))
            }
        }

    suspend fun getUpComingMovies(): Flow<ApiResponse<List<MovieResponse>>> =
        channelFlow {
            try {
                val response = apiService.getUpComingMovies()
                val data = response.results
                if (data.isNotEmpty()) {
                    send(ApiResponse.Success(data))
                } else {
                    send(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                send(ApiResponse.Error(e.message.toString()))
            }
        }

    fun getCreditMovie(id: Int): Flow<List<CreditResponse>> =
        channelFlow {
            try {
                val response = apiService.getCredits(id)
                val data = response.cast

                if (data.isNotEmpty()) {
                    send(data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)

    fun getDetailMovie(id: Int): Flow<MovieResponse> =
        channelFlow {
            try {
                val data = apiService.getDetailMovie(id)
                send(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

}