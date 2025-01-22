package com.tcivileva.nata.sekveniya.films.project.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["filmId", "genre"],
    foreignKeys = [
        ForeignKey(
            entity = FilmEntity::class,
            parentColumns = ["filmId"],
            childColumns = ["filmId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),
         ForeignKey(
             entity = GenreEntity::class,
             parentColumns = ["genre"],
             childColumns = ["genre"],
             onUpdate = ForeignKey.CASCADE,
             onDelete = ForeignKey.CASCADE
         )
    ]
)

data class FilmGenreCross(
    val filmId:Int,
    val genre: String
)