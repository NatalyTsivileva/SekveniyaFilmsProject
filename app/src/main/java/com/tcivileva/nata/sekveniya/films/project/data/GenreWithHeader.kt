package com.tcivileva.nata.sekveniya.films.project.data

import androidx.annotation.StringRes

data class GenreWithHeader(
    @StringRes val headerRes:Int,
    val genres:List<Genre>
)