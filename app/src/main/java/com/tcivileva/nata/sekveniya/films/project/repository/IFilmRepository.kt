package com.tcivileva.nata.sekveniya.films.project.repository

import com.tcivileva.nata.sekveniya.films.project.data.Film
import com.tcivileva.nata.sekveniya.films.project.data.Genre

interface IFilmRepository {

    suspend fun getFilmsList(): List<Film>

    suspend fun getFilmList(genre: String): List<Film>

    suspend fun getGenresList(): List<Genre>

    suspend fun getFilmData(id:Int): Film

}