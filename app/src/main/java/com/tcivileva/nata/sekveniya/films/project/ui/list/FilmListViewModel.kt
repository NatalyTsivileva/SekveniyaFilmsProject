package com.tcivileva.nata.sekveniya.films.project.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcivileva.nata.sekveniya.films.project.data.FilmData
import com.tcivileva.nata.sekveniya.films.project.data.FilmGenre
import com.tcivileva.nata.sekveniya.films.project.data.FilmHeader
import com.tcivileva.nata.sekveniya.films.project.data.FilmSkeleton
import com.tcivileva.nata.sekveniya.films.project.data.IAdapterData
import com.tcivileva.nata.sekveniya.films.project.repository.IFilmRepository
import com.tcivileva.nata.sekveniya.films.project.ui.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import retrofit2.HttpException
import java.net.SocketTimeoutException

class FilmListViewModel(
    private val repository: IFilmRepository
):ViewModel() {

    private val _filmsListFlow: MutableStateFlow<LoadingState<List<FilmData>>> = MutableStateFlow(LoadingState.Loading())
    val filmsListFlow = _filmsListFlow.asStateFlow()

    private val _genresListFlow:MutableStateFlow<List<IAdapterData>> = MutableStateFlow(listOf(FilmSkeleton()))
    val genresListFlow = _genresListFlow.asStateFlow()

    fun getFilmsList(){
       viewModelScope.launch(Dispatchers.IO) {
           try {
             val films = repository.getFilmsList()
             val state = LoadingState.Success(films)
             _filmsListFlow.tryEmit(state)
             _genresListFlow.tryEmit(getAllGenres())
           }catch (s: SocketTimeoutException){
               val state = LoadingState.ConnectionError<List<FilmData>>(s.message?:"")
               _filmsListFlow.tryEmit(state)
           }catch (h: HttpException){
               val msg = "${h.code()} : ${h.message()}"
               val state = LoadingState.ConnectionError<List<FilmData>>(msg)
               _filmsListFlow.tryEmit(state)
           }catch (t:Throwable){
              val state = LoadingState.Error<List<FilmData>>(t.message?:"")
               _filmsListFlow.tryEmit(state)
           }
       }
    }

    suspend fun getAllGenres():List<IAdapterData>{
        //TODO заменить на получение текста из ресов!
        val genres:MutableList<IAdapterData> = mutableListOf(FilmHeader("Жанры"))

        repository.getGenresList().map {
            val genre = FilmGenre(it, false)
            genres.add(genre)
        }
        //TODO заменить на получение текста из ресов!
        genres.add(FilmHeader("Фильмы"))

        return genres
    }
}