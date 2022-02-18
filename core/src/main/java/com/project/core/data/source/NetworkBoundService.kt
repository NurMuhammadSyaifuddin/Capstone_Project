package com.project.core.data.source

import com.project.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkBoundService<ResultType, RequestType> {

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDb().first()

        if (shoulFetch(dbSource)){
            emit(Resource.Loading())

            when(val apiResponse = createCall().first()) {
                is ApiResponse.Success -> {
                    saveCallResult(apiResponse.data)
                    emitAll(loadFromDb().map { Resource.Success(it) })
                }
                is ApiResponse.Empty -> {
                    emitAll(loadFromDb().map { Resource.Success(it) })
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error<ResultType>(apiResponse.errorMessage))
                }
            }
        }else{
            emitAll(loadFromDb().map { Resource.Success(it) })
        }
    }

    protected abstract suspend fun saveCallResult(data: RequestType)

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract fun shoulFetch(data: ResultType): Boolean

    protected abstract fun loadFromDb(): Flow<ResultType>

    fun asFlow(): Flow<Resource<ResultType>> = result


}