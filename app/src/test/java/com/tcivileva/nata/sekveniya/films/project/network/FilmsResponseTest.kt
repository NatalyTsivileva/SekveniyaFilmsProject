package com.tcivileva.nata.sekveniya.films.project.network

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert
import org.junit.Test


class FilmsResponseTest{

    val dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when(request.path){
                "/films.json"->MockResponse()
                    .setResponseCode(200)
                    .setBody(FILMS_RESPONSE)

                else-> MockResponse().setResponseCode(404)
            }
        }

    }

    @Test
    fun testFilmResponse() = runBlocking{
        val server = MockWebServer()
        server.dispatcher = dispatcher

        server.start()

        val actualFilmsList = NetworkClient
            .createApi(server.url("/"))
            .getFilms()

        val expectedFilmsList = FilmsResponse(
            films = listOf(
                FilmsResponse.Film(
                    id = 326,
                    localizedName = "Побег из Шоушенка",
                    name = "The Shawshank Redemption",
                    year = 1994,
                    rating = 9.196,
                    imageUrl = "https://st.kp.yandex.net/images/film_iphone/iphone360_326.jpg",
                    description = "Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.",
                    genres = listOf("драма")

                )
            )
        )
        Assert.assertEquals(expectedFilmsList,actualFilmsList)
        server.shutdown()
    }
}