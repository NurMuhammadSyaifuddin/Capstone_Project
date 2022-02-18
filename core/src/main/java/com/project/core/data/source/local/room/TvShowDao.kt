package com.project.core.data.source.local.room

import androidx.room.*
import com.project.core.data.source.local.entitiy.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @Query("SELECT * FROM tvshow WHERE type = :type")
    fun getAllTvShow(type: String): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM tvshow WHERE id = :id")
    fun getTvShowById(id: Int): Flow<TvShowEntity>

    @Query("SELECT * FROM tvshow WHERE name LIKE '%' || :name || '%'")
    fun getSearchTvShow(name: String): Flow<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShow(tvshow: List<TvShowEntity>)

    @Query("SELECT * FROM tvshow WHERE is_favorite = 1")
    fun getFavoriteTvShow(): Flow<List<TvShowEntity>>

    @Update(entity = TvShowEntity::class)
    suspend fun updateTvShow(tvshow: TvShowEntity)

}