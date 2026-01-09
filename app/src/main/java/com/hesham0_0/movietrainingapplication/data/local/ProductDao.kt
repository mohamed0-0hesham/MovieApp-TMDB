package com.hesham0_0.movietrainingapplication.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hesham0_0.movietrainingapplication.domain.models.MovieItem


@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(item: MovieItem)

    @Query("SELECT * FROM Movie_table ORDER BY Id ASC")
    fun readAllData(): List<MovieItem>

    @Update
    suspend fun update(item: MovieItem)

    @Delete
    suspend fun delete(item: MovieItem)

    @Query("DELETE FROM Movie_table")
    suspend fun deleteAll()

//    @Query("SELECT * FROM Genre_table ORDER BY Id ASC")
//    fun readAllGenre(): List<Genre>
//
//    @Query("DELETE FROM Genre_table")
//    suspend fun deleteAllGenre()

}
