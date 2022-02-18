package com.project.core.data.source.local

import com.project.core.data.source.local.entitiy.TvShowEntity
import com.project.core.data.source.local.room.TvShowDao
import kotlinx.coroutines.flow.Flow

class TvShowLocalDataSource(private val tvShowDao: TvShowDao) {

    fun getAllTvShow(type: String): Flow<List<TvShowEntity>> = tvShowDao.getAllTvShow(type)

    fun getTvShowById(id: Int): Flow<TvShowEntity> = tvShowDao.getTvShowById(id)

    fun getSearchTvShow(name: String): Flow<List<TvShowEntity>> = tvShowDao.getSearchTvShow(name)

    suspend fun insertTvShow(tvshow: List<TvShowEntity>) = tvShowDao.insertTvShow(tvshow)

    fun getFavoriteTvShow(): Flow<List<TvShowEntity>> = tvShowDao.getFavoriteTvShow()

    suspend fun setFavoriteTvShow(tvshow: TvShowEntity){
        tvshow.isFavorite = !tvshow.isFavorite
        tvShowDao.updateTvShow(tvshow)
    }

}