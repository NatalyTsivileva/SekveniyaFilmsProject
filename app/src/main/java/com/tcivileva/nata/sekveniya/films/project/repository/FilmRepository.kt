package com.tcivileva.nata.sekveniya.films.project.repository

import com.tcivileva.nata.sekveniya.films.project.data.DataConverter
import com.tcivileva.nata.sekveniya.films.project.data.Film
import com.tcivileva.nata.sekveniya.films.project.data.Genre
import com.tcivileva.nata.sekveniya.films.project.database.FilmDatabase
import com.tcivileva.nata.sekveniya.films.project.network.FilmsAPI

class FilmRepository(
  private val api:FilmsAPI,
  private val database: FilmDatabase
):IFilmRepository {

    override suspend fun getFilmsList(): List<Film> {
        val filmsResponse = api.getFilms()

        val entityMap = DataConverter.fromResponseToEntity(filmsResponse)
        //кэшируем в БД
        entityMap.forEach { filmEntity, genreEntities ->
           database
               .filmDao()
               .insertFilmWithGenres(filmEntity,genreEntities)
        }

        return DataConverter.fromResponseToData(filmsResponse)
    }

    override suspend fun getFilmList(genre: String): List<Film> {
        val filmsWithGenres = database.filmDao().getFilmsByGenre(genre)
        return DataConverter.fromEntityToData(filmsWithGenres)
    }

    override suspend fun getGenresList(): List<Genre> {
        return database.genreDao().getGenres().map {
            Genre(it.genre, false)
        }
    }

    override suspend fun getGenresList(selectedGenre: String): List<Genre> {
        return database.genreDao().getGenres().map {
            val isSelected = selectedGenre == it.genre
            Genre(it.genre, isSelected)
        }
    }


    override suspend fun getFilmData(id: Int): Film {
        val filmWithGenre = database.filmDao().getFilmWithGenres(id)
        return DataConverter
            .fromEntityToData(listOf(filmWithGenre))
            .firstOrNull()?:Film()
    }
}