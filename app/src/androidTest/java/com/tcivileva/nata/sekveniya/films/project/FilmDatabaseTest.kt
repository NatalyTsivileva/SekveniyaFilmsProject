package com.tcivileva.nata.sekveniya.films.project

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.tcivileva.nata.sekveniya.films.project.database.FilmDatabase
import com.tcivileva.nata.sekveniya.films.project.database.entity.FilmEntity
import com.tcivileva.nata.sekveniya.films.project.database.entity.GenreEntity
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FilmDatabaseTest {

    private var database: FilmDatabase? = null

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, FilmDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }


    @After
    fun shutDown(){
        database?.close()
        database = null
    }

    @Test
    fun testFilmAdding(){
        database
            ?.filmDao()
            ?.insertFilmWithGenres(film = expectedFilm, genres = expectedGenres)

        val actualFilmsWithGenres = database?.filmDao()?.getFilmsWithGenres()
        val firstFilm = actualFilmsWithGenres?.firstOrNull()?.film
        val firstFilmGenres = actualFilmsWithGenres?.firstOrNull()?.genres

        Assert.assertEquals(expectedFilm, firstFilm)
        Assert.assertEquals(expectedGenres, firstFilmGenres)
    }

    companion object {
        val expectedFilm = FilmEntity(
            filmId = 435,
            localizedName = "Зеленая миля",
            name = "The Green Mile",
            year = 1999,
            rating = 9.064,
            image = "https://st.kp.yandex.net/images/film_iphone/iphone360_435.jpg",
            description = "Обвиненный в страшном преступлении, Джон Коффи оказывается в блоке смертников тюрьмы «Холодная гора». Вновь прибывший обладал поразительным ростом и был пугающе спокоен, что, впрочем, никак не влияло на отношение к нему начальника блока Пола Эджкомба, привыкшего исполнять приговор. Гигант удивил всех позже, когда выяснилось, что он обладает невероятной магической силой…"
            )

        val expectedGenres = listOf<GenreEntity>(
            GenreEntity("детектив"),
            GenreEntity("драма"),
            GenreEntity("криминал"),
            GenreEntity("фэнтези")
        )
    }
}