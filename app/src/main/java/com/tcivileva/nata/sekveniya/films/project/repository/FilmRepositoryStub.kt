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
                id=326,
                name = "The Shawshank Redemption",
                nameRu = "Побег из Шоушенка",
                year=1994,
                rating = 9.196,
                image="https://st.kp.yandex.net/images/film_iphone/iphone360_326.jpg",
                description ="Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.",
                genres = listOf("драма")
            ),
            Film(
                id=435,
                name = "The Green Mile",
                nameRu = "Зеленая миля",
                year = 1999,
                rating = 9.064,
                image="https://st.kp.yandex.net/images/film_iphone/iphone360_435.jpg",
                description = "Обвиненный в страшном преступлении, Джон Коффи оказывается в блоке смертников тюрьмы «Холодная гора». Вновь прибывший обладал поразительным ростом и был пугающе спокоен, что, впрочем, никак не влияло на отношение к нему начальника блока Пола Эджкомба, привыкшего исполнять приговор. Гигант удивил всех позже, когда выяснилось, что он обладает невероятной магической силой…",
                genres = listOf("мелодрамма","комедия","трагедия")
            ),
            Film(
                id=3,
                name = "Test film",
                nameRu = "Тестовый фильм",
                year=2025,
                rating = 10.0,
                image="https://st.kp.yandex.net/images/film_iphone/iphone360_326.jpg",
                description = "Фильм для тестирования СПИСКА без обращения к API",
                genres = listOf("фэнтези", "драма", "криминал", "детектив")
            )
        )
    }

    override suspend fun getFilmList(genre: String): List<Film> {
        return  listOf( Film(
            id=2,
            name = "Test film",
            nameRu = "Тестовый фильм",
            year=2025,
            rating = 10.0,
            image="https://st.kp.yandex.net/images/film_iphone/iphone360_435.jpg",
            description = "Фильм для тестирования СПИСКА без обращения к API",
            genres = listOf("мелодрамма","комедия","трагедия")
        ))
    }

    override suspend fun getGenresList(): List<Genre> {
        return listOf(
            Genre("драма",false),
            Genre("мелодрамма",false),
            Genre("комедия", false),
            Genre("трагедия", false),
            Genre("фэнтези", false),
            Genre("криминал", false),
            Genre("детектив", false),
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