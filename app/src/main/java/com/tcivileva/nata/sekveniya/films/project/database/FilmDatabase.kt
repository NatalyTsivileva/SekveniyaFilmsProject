package com.tcivileva.nata.sekveniya.films.project.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tcivileva.nata.sekveniya.films.project.database.dao.FilmDao
import com.tcivileva.nata.sekveniya.films.project.database.dao.GenreDao
import com.tcivileva.nata.sekveniya.films.project.database.entity.FilmEntity
import com.tcivileva.nata.sekveniya.films.project.database.entity.FilmGenreCross
import com.tcivileva.nata.sekveniya.films.project.database.entity.GenreEntity

@Database(
    exportSchema = false,
    version = 1,
    entities = [
      FilmEntity::class,
      GenreEntity::class,
      FilmGenreCross::class
    ]
)
abstract class FilmDatabase: RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun filmDao(): FilmDao
}