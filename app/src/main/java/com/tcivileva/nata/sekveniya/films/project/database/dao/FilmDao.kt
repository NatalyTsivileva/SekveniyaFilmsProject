package com.tcivileva.nata.sekveniya.films.project.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.tcivileva.nata.sekveniya.films.project.database.entity.FilmEntity
import com.tcivileva.nata.sekveniya.films.project.database.entity.FilmGenreCross
import com.tcivileva.nata.sekveniya.films.project.database.entity.FilmWithGenres
import com.tcivileva.nata.sekveniya.films.project.database.entity.GenreEntity

@Dao
interface FilmDao:GenreDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFilm(film:FilmEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFilmAndGenreCross(cross:FilmGenreCross)

    @Query("SELECT * FROM Film")
    fun getFilmsWithGenres():List<FilmWithGenres>

    @Query("SELECT * FROM Film WHERE filmId=:id")
    fun getFilmWithGenres(id:Int):FilmWithGenres

    @Query("SELECT f.filmId, f.localizedName,f.name, f.year, f.rating, f.image, f.description, g.genre " +
            "FROM Film f " +
            "INNER JOIN FilmGenreCross c " +
            "ON f.filmId=c.filmId " +
            "INNER JOIN Genre g " +
            "ON c.genre=g.genre "+
            "WHERE c.genre=:genre")
    fun getFilmsByGenre(genre:String):List<FilmWithGenres>

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