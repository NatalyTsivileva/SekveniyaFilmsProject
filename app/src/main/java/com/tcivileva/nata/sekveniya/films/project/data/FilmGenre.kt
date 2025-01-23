package com.tcivileva.nata.sekveniya.films.project.data


data class FilmGenre(
    val genre:String,
    val isSelected:Boolean
):IFilmData {

    override fun getDataType(): FilmDataType {
        return FilmDataType.GENRE
    }

    override fun isTheSame(other: IFilmData): Boolean {
        return this==other
    }
}