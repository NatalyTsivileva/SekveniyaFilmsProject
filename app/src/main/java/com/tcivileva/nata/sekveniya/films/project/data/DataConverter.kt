package com.tcivileva.nata.sekveniya.films.project.data

import com.tcivileva.nata.sekveniya.films.project.database.entity.FilmEntity
import com.tcivileva.nata.sekveniya.films.project.database.entity.FilmWithGenres
import com.tcivileva.nata.sekveniya.films.project.database.entity.GenreEntity
import com.tcivileva.nata.sekveniya.films.project.network.FilmsResponse
import java.util.Locale

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
                val value = genre
                    .replaceFirstChar {
                        if (it.isLowerCase())
                            it.titlecase(Locale.ROOT)
                        else it.toString()
                    }
                GenreEntity(value)
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
        }.sortedBy { it.nameRu }
    }

    fun fromEntityToData(entities:List<FilmWithGenres>): List<Film>{
       return entities.map {
            Film(
                id = it.film.filmId,
                name = it.film.name,
                nameRu = it.film.localizedName,
                year = it.film.year,
                rating = it.film.rating,
                image = it.film.image,
                description = it.film.description,
                genres = it.genres.map{ it.genre }
            )
        }.sortedBy { it.nameRu }
    }
}