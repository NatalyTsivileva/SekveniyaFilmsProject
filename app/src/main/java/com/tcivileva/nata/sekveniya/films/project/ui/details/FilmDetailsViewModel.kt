package com.tcivileva.nata.sekveniya.films.project.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcivileva.nata.sekveniya.films.project.data.Film
import com.tcivileva.nata.sekveniya.films.project.data.FilmsWithHeader
import com.tcivileva.nata.sekveniya.films.project.repository.IFilmRepository
import com.tcivileva.nata.sekveniya.films.project.ui.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class FilmDetailsViewModel(
   private val repository: IFilmRepository
):ViewModel() {

    private val _filmsDetails: MutableStateFlow<Film> = MutableStateFlow(Film())
    val filmsDetails = _filmsDetails.asStateFlow()

    fun getFilmInfo(filmId:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = repository.getFilmData(filmId)
                Timber.d("FilmDetailsViewModel:getFilmInfo(${filmId}).Name=${data.name}, nameRu=${data.nameRu}")
                _filmsDetails.tryEmit(data)
            }catch (t:Throwable){
                Timber.d("Ошибка загрузки детальной информации о фильме:${t.message}")
            }
        }
    }
}