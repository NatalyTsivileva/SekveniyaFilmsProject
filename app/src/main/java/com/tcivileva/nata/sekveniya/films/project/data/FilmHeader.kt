package com.tcivileva.nata.sekveniya.films.project.data

data class FilmHeader(
    val text:String
):IFilmData {

    override fun getDataType(): FilmDataType {
        return FilmDataType.HEADER
    }

    override fun isTheSame(other: IFilmData): Boolean {
        return this==other
    }
}