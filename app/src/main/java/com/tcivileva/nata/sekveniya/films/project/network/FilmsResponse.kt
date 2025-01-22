package com.tcivileva.nata.sekveniya.films.project.network


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmsResponse(
    val films: List<Film> = listOf()
) {
    @Serializable
    data class Film(
        val description: String = "",
        val genres: List<String> = listOf(),
        val id: Int = 0,
        @SerialName("image_url")
        val imageUrl: String = "",
        @SerialName("localized_name")
        val localizedName: String = "",
        val name: String = "",
        val rating: Double? = 0.0,
        val year: Int = 0
    )
}