package com.tcivileva.nata.sekveniya.films.project.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Genre")
data class GenreEntity (
    @PrimaryKey
    val genre: String
)