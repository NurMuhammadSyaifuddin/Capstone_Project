package com.project.core.data.source.local.room

import androidx.room.*
import com.project.core.data.source.local.entitiy.MovieEntiity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE type = :type")
    fun getAllMovies(type: String): Flow<List<MovieEntiity>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieById(id: Int): Flow<MovieEntiity>

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :title || '%'")
    fun getSearchMovie(title: String): Flow<List<MovieEntiity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntiity>)

    @Query("SELECT * FROM movies WHERE is_favorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntiity>>

    @Update(entity = MovieEntiity::class)
    suspend fun updateMovie(movie: MovieEntiity)

}