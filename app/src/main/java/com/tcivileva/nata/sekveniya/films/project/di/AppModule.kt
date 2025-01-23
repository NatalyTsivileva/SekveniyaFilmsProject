package com.tcivileva.nata.sekveniya.films.project.di

import androidx.room.Room
import com.tcivileva.nata.sekveniya.films.project.database.FilmDatabase
import com.tcivileva.nata.sekveniya.films.project.network.FilmsAPI
import com.tcivileva.nata.sekveniya.films.project.network.NetworkClient
import com.tcivileva.nata.sekveniya.films.project.repository.FilmRepository
import com.tcivileva.nata.sekveniya.films.project.repository.IFilmRepository
import com.tcivileva.nata.sekveniya.films.project.ui.list.FilmListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<FilmsAPI>{ NetworkClient.createApi() }

    single<FilmDatabase> { Room.databaseBuilder(get(), FilmDatabase::class.java, "film_database").build() }

    single<IFilmRepository> { FilmRepository( get(), get()) }

    viewModel { FilmListViewModel(get()) }
}