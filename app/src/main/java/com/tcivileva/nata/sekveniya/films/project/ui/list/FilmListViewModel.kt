package com.tcivileva.nata.sekveniya.films.project.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcivileva.nata.sekveniya.films.project.R
import com.tcivileva.nata.sekveniya.films.project.data.Film
import com.tcivileva.nata.sekveniya.films.project.data.FilmsWithHeader
import com.tcivileva.nata.sekveniya.films.project.data.GenreWithHeader
import com.tcivileva.nata.sekveniya.films.project.repository.IFilmRepository
import com.tcivileva.nata.sekveniya.films.project.ui.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class FilmListViewModel(
    private val repository: IFilmRepository
):ViewModel() {

    private val _filmsListFlow: MutableStateFlow<LoadingState<List<FilmsWithHeader>>> = MutableStateFlow(LoadingState.Loading())
    val filmsListFlow = _filmsListFlow.asStateFlow()

    private val _genresListFlow:MutableStateFlow<List<GenreWithHeader>> = MutableStateFlow(listOf())
    val genresListFlow = _genresListFlow.asStateFlow()

    fun getFilmsList(){
       viewModelScope.launch(Dispatchers.IO) {
           try {
             val films = repository.getFilmsList()
             val filmsWithHeader = FilmsWithHeader(R.string.header_films, films)
             val state = LoadingState.Success(listOf(filmsWithHeader))
             _filmsListFlow.tryEmit(state)

           val genres = repository.getGenresList()
           val genreWithHeader = GenreWithHeader(headerRes = R.string.header_genres, genres)
            _genresListFlow.tryEmit(listOf(genreWithHeader))

           }catch (s: SocketTimeoutException){
               val state = LoadingState.ConnectionError<List<FilmsWithHeader>>(s.message?:"")
               _filmsListFlow.tryEmit(state)
           }catch (h: HttpException){
               val msg = "${h.code()} : ${h.message()}"
               val state = LoadingState.ConnectionError<List<FilmsWithHeader>>(msg)
               _filmsListFlow.tryEmit(state)
           }catch (t:Throwable){
              val state = LoadingState.Error<List<FilmsWithHeader>>(t.message?:"")
               _filmsListFlow.tryEmit(state)
           }
       }
    }
}