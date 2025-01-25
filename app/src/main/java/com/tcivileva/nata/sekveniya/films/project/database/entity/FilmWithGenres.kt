package com.tcivileva.nata.sekveniya.films.project.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class FilmWithGenres(
    @Embedded val film: FilmEntity,
    @Relation(
        parentColumn = "filmId",
        entityColumn = "genre",
        associateBy = Junction(FilmGenreCross::class)
    )
    val genres: List<GenreEntity>
)