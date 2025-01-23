package com.tcivileva.nata.sekveniya.films.project.ui

sealed class LoadingState<T>{
  class Loading<T>:LoadingState<T>()
  data class Success<T>(val data:T):LoadingState<T>()
  data class ConnectionError<T>(val message:String):LoadingState<T>()
  data class Error<T>(val message:String): LoadingState<T>()
}