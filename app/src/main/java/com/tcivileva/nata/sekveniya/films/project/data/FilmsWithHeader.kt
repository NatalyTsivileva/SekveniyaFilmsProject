package com.tcivileva.nata.sekveniya.films.project.data

import androidx.annotation.StringRes

data class FilmsWithHeader(
    @StringRes val headerRes: Int,
    val films: List<Film>
)