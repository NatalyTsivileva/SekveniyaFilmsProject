package com.tcivileva.nata.sekveniya.films.project.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcivileva.nata.sekveniya.films.project.R
import com.tcivileva.nata.sekveniya.films.project.data.FilmsWithHeader
import com.tcivileva.nata.sekveniya.films.project.data.Genre
import com.tcivileva.nata.sekveniya.films.project.data.GenreWithHeader
import com.tcivileva.nata.sekveniya.films.project.repository.IFilmRepository
import com.tcivileva.nata.sekveniya.films.project.ui.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.net.SocketTimeoutException

class FilmListViewModel(
    private val repository: IFilmRepository
):ViewModel() {

    private val _filmsListFlow: MutableStateFlow<LoadingState<List<FilmsWithHeader>>> = MutableStateFlow(LoadingState.Loading())
    val filmsListFlow = _filmsListFlow.asStateFlow()

    private val _genresListFlow:MutableStateFlow<List<GenreWithHeader>> = MutableStateFlow(listOf())
    val genresListFlow = _genresListFlow.asStateFlow()

    private var selectedGenre: String = ""

    fun loadData(){
        if(selectedGenre.isEmpty()){
            getFilms()
        }else{
            getFilms(selectedGenre)
        }
    }

    fun toggleGenre(genre: Genre){
        if(selectedGenre==genre.genre){
            selectedGenre = ""
        } else {
            selectedGenre = genre.genre
        }
        getFilms(selectedGenre)
    }

    private fun getFilms(){
       viewModelScope.launch(Dispatchers.IO) {
           try {
             _filmsListFlow.tryEmit(LoadingState.Loading())

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
               Timber.d("getFilms() SocketTimeoutException:${s.message}")
           }catch (h: HttpException){
               val msg = "${h.code()} : ${h.message()}"
               val state = LoadingState.ConnectionError<List<FilmsWithHeader>>(msg)
               _filmsListFlow.tryEmit(state)
               Timber.d("getFilms() HttpException:${msg}")
           }catch (t:Throwable){
              val state = LoadingState.Error<List<FilmsWithHeader>>(t.message?:"")
               _filmsListFlow.tryEmit(state)
               Timber.d("getFilms() Error!:${t.message}")
           }
       }
    }

    private fun getFilms(genre:String){
        viewModelScope.launch(Dispatchers.IO){
            try {
                val newGenreList:List<Genre> = repository.getGenresList(selectedGenre)
                    .map {
                           if(it.genre==genre){
                               it.copy(isSelected = true)
                           }else{
                               it.copy(isSelected = false)
                           }
                        }

                val genreWithHeader = GenreWithHeader(headerRes = R.string.header_genres, newGenreList)
                _genresListFlow.tryEmit(listOf(genreWithHeader))

                if (newGenreList.all{!it.isSelected}) selectedGenre = ""

                if (selectedGenre.isNotEmpty()) {
                    _filmsListFlow.tryEmit(LoadingState.Loading())

                    val films = repository.getFilmList(selectedGenre)
                    val filmsWithHeader = FilmsWithHeader(R.string.header_films, films)
                    val state = LoadingState.Success(listOf(filmsWithHeader))
                    _filmsListFlow.tryEmit(state)
                }else{
                    getFilms()
                }
            }catch (t:Throwable){
                val state = LoadingState.Error<List<FilmsWithHeader>>(t.message?:"")
                _filmsListFlow.tryEmit(state)
                Timber.d("getFilms(genre:String) Error!:${t.message}")
            }
        }
    }

}