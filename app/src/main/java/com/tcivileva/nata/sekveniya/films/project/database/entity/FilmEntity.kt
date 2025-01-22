package com.tcivileva.nata.sekveniya.films.project.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Film")
data class FilmEntity(
  @PrimaryKey
  val filmId:Int = 0,
  val localizedName:String = "",
  val name:String = "",
  val year:Short = 0,
  val rating:Double = 0.0,
  val image:String = "",
  val description:String = ""
)