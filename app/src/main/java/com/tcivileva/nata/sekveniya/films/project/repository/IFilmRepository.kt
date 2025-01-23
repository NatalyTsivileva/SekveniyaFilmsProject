package com.tcivileva.nata.sekveniya.films.project.repository

import com.tcivileva.nata.sekveniya.films.project.data.FilmData

interface IFilmRepository {

    suspend fun getFilmsList():List<FilmData>

    suspend fun getGenresList():List<String>

    suspend fun getFilmData(id:Int): FilmData

}