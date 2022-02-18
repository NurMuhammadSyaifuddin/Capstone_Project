package com.project.core.data.source.remote

import com.project.core.data.source.remote.network.ApiResponse
import com.project.core.data.source.remote.network.ApiService
import com.project.core.data.source.remote.response.CreditResponse
import com.project.core.data.source.remote.response.tvshow.TvShowResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

@ExperimentalCoroutinesApi
class TvShowRemoteDataSource(private val apiService: ApiService) {
    suspend fun getPopularTvShow(): Flow<ApiResponse<List<TvShowResponse>>> =
        channelFlow {
            try {
                val response = apiService.getPopularTvShow()
                val data = response.results
                if (data.isNotEmpty()){
                    send(ApiResponse.Success(data))
                }else{
                    send(ApiResponse.Empty)
                }
            }catch (e: Exception){
                e.printStackTrace()
                send(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getAiringTodayTvShow(): Flow<ApiResponse<List<TvShowResponse>>> =
        channelFlow {
            try {
                val response = apiService.getAiringTodayTvShow()
                val data = response.results
                if (data.isNotEmpty()){
                    send(ApiResponse.Success(data))
                }else{
                    send(ApiResponse.Empty)
                }
            }catch (e: Exception){
                e.printStackTrace()
                send(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    suspend fun getTopRatedTvShow(): Flow<ApiResponse<List<TvShowResponse>>> =
        channelFlow {
            try {
                val response = apiService.getTopRatedTvShow()
                val data = response.results
                if (data.isNotEmpty()){
                    send(ApiResponse.Success(data))
                }else{
                    send(ApiResponse.Empty)
                }
            }catch (e: Exception){
                e.printStackTrace()
                send(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getCreditTvShow(id: Int): Flow<List<CreditResponse>> =
        channelFlow {
            try {
                val response = apiService.getCreditsTvShow(id)
                val data = response.cast

                if (data.isNotEmpty()) {
                    send(data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.flowOn(Dispatchers.IO)

    fun getDetailTvShow(id: Int): Flow<TvShowResponse> =
        channelFlow {
            try {
                val data = apiService.getDetailTvShow(id)
                send(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
}