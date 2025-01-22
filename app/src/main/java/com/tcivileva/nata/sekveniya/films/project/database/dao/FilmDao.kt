package com.tcivileva.nata.sekveniya.films.project.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.tcivileva.nata.sekveniya.films.project.database.entity.FilmEntity
import com.tcivileva.nata.sekveniya.films.project.database.entity.FilmGenreCross
import com.tcivileva.nata.sekveniya.films.project.database.entity.FilmWithGenres
import com.tcivileva.nata.sekveniya.films.project.database.entity.GenreEntity

@Dao
interface FilmDao:GenreDao {

    @Insert
    fun insertFilm(film:FilmEntity)

    @Insert
    fun insertFilmAndGenreCross(cross:FilmGenreCross)

    @Query("SELECT * FROM Film")
    fun getFilmsWithGenres():List<FilmWithGenres>

    @Transaction
    fun insertFilmWithGenres(film:FilmEntity, genres: List<GenreEntity>){

        insertFilm(film)

        genres.map { GenreEntity(genre = it.genre) }.forEach { genre ->
            insertGenre(genre)
            val filmAndGenre = FilmGenreCross(filmId = film.filmId, genre = genre.genre)
            insertFilmAndGenreCross(filmAndGenre)
        }
    }
}