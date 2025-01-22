package com.tcivileva.nata.sekveniya.films.project.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tcivileva.nata.sekveniya.films.project.database.entity.GenreEntity

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGenre(genre:GenreEntity)

    @Query("SELECT * FROM Genre")
    fun getGenres():List<GenreEntity>
}