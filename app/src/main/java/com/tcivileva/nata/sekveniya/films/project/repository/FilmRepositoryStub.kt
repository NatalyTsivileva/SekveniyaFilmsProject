package com.tcivileva.nata.sekveniya.films.project.repository

import com.tcivileva.nata.sekveniya.films.project.data.Film
import com.tcivileva.nata.sekveniya.films.project.data.Genre
import com.tcivileva.nata.sekveniya.films.project.database.FilmDatabase
import com.tcivileva.nata.sekveniya.films.project.network.FilmsAPI

class FilmRepositoryStub(
    api:FilmsAPI,
    database: FilmDatabase
):IFilmRepository {

    override suspend fun getFilmsList(): List<Film> {
        return listOf(
            Film(
                id=1,
                name = "Test film",
                nameRu = "Тестовый фильм",
                year=2025,
                rating = 10.0,
                image="https://st.kp.yandex.net/images/film_iphone/iphone360_326.jpg",
                description = "Фильм для тестирования СПИСКА без обращения к API",
                genres = listOf("мелодрамма","комедия","трагедия")
            ),
            Film(
                id=2,
                name = "Test film",
                nameRu = "Тестовый фильм",
                year=2025,
                rating = 10.0,
                image="https://st.kp.yandex.net/images/film_iphone/iphone360_435.jpg",
                description = "Фильм для тестирования СПИСКА без обращения к API",
                genres = listOf("мелодрамма","комедия","трагедия")
            )
        )
    }

    override suspend fun getGenresList(): List<Genre> {
        return listOf(
            Genre("Драма",false),
            Genre("Приключения",false),
            Genre("Боевик", isSelected = false)
        )
    }

    override suspend fun getFilmData(id: Int): Film {
        return Film(
            id=2,
            name = "Test film",
            nameRu = "Тестовый фильм",
            year=2025,
            rating = 10.0,
            image="https://st.kp.yandex.net/images/film_iphone/iphone360_326.jpg",
            description = "Фильм для тестирования ФИЛЬМА ПО ID без обращения к API",
            genres = listOf("сюрреализм","артхаус","трагедия")
        )
    }
}