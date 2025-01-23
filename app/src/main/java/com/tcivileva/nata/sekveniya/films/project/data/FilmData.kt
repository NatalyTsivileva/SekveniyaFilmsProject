package com.tcivileva.nata.sekveniya.films.project.data

data class FilmData(
    val id:Int = 0,
    val name: String = "",
    val nameRu:String = "",
    val year: Int = 0,
    val rating: Double = 0.0,
    val image: String = "",
    val description: String = "",
    val genres: List<String> = listOf()
):IFilmData{

    override fun getDataType(): FilmDataType {
        return FilmDataType.FILM_DATA
    }

    override fun isTheSame(other: IFilmData): Boolean {
        return this==other
    }
}
