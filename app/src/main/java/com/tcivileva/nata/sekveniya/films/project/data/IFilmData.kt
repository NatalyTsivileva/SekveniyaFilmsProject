package com.tcivileva.nata.sekveniya.films.project.data

interface IFilmData {
    fun getDataType():FilmDataType

    fun isTheSame(other:IFilmData):Boolean
}