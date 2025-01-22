package com.tcivileva.nata.sekveniya.films.project.network

import retrofit2.http.GET

interface FilmsAPI {
    @GET("films.json")
    suspend fun getFilms():FilmsResponse
}