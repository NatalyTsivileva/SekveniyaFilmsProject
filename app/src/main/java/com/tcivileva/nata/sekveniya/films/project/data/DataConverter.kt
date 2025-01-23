package com.tcivileva.nata.sekveniya.films.project.data

import com.tcivileva.nata.sekveniya.films.project.database.entity.FilmEntity
import com.tcivileva.nata.sekveniya.films.project.database.entity.GenreEntity
import com.tcivileva.nata.sekveniya.films.project.network.FilmsResponse

object DataConverter {

    
    fun fromResponseToEntity(response:FilmsResponse):Map<FilmEntity, List<GenreEntity>>{
        val result = mutableMapOf<FilmEntity,List<GenreEntity>>()

        response.films.forEach {

            val film = FilmEntity(
                filmId = it.id,
                localizedName = it.localizedName,
                name = it.name,
                year = it.year,
                rating = it.rating,
                image = it.imageUrl,
                description = it.description
            )

            val genres = it.genres.map { genre->
                GenreEntity(genre)
            }

            result[film] = genres
        }
        return result
    }

    fun fromResponseToData(response:FilmsResponse): List<Film> {
       return response.films.map {
            Film(
                id = it.id,
                name = it.name,
                nameRu = it.localizedName,
                year = it.year,
                rating = it.rating,
                image = it.imageUrl,
                description = it.description,
                genres = it.genres
            )
        }
    }
}