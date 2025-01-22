package com.tcivileva.nata.sekveniya.films.project.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


object NetworkClient {
    fun createApi(url: HttpUrl = "https://s3-eu-west-1.amazonaws.com/sequeniatesttask/".toHttpUrl()): FilmsAPI {

        val interceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val client = OkHttpClient
            .Builder()
            .addNetworkInterceptor(interceptor)
            .build()

        val json = Json{
            coerceInputValues = true
        }

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(url)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        return retrofit.create(FilmsAPI::class.java)
    }
}