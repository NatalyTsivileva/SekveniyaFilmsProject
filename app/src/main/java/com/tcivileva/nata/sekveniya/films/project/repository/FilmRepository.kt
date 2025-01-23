package com.tcivileva.nata.sekveniya.films.project.repository

import com.tcivileva.nata.sekveniya.films.project.data.FilmData
import com.tcivileva.nata.sekveniya.films.project.database.FilmDatabase
import com.tcivileva.nata.sekveniya.films.project.network.FilmsAPI

class FilmRepository(
    api:FilmsAPI,
    database: FilmDatabase
):IFilmRepository {

    override suspend fun getFilmsList(): List<FilmData> {
        //TODO("Not yet implemented")
        return listOf(
            FilmData(
                id=1,
                name = "Test film",
                nameRu = "Тестовый фильм",
                year=2025,
                rating = 10.0,
                image="https://st.kp.yandex.net/images/film_iphone/iphone360_326.jpg",
                description = "Фильм для тестирования СПИСКА без обращения к API",
                genres = listOf("мелодрамма","комедия","трагедия")
            )
        )
    }

    override suspend fun getFilmData(id: Int): FilmData {
       // TODO("Not yet implemented")
        return FilmData(
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