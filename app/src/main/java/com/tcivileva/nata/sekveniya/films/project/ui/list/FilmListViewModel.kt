package com.tcivileva.nata.sekveniya.films.project.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcivileva.nata.sekveniya.films.project.data.FilmData
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

    fun getFilmsList(){
       viewModelScope.launch(Dispatchers.IO) {
           try {
             val films = repository.getFilmsList()
             val state = LoadingState.Success(films)
             _filmsListFlow.tryEmit(state)

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
}