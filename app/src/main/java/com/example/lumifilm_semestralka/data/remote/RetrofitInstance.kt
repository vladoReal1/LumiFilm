package com.example.lumifilm_semestralka.data.remote

import com.example.lumifilm_semestralka.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// AI assisted: Singleton objekt pre Retrofit inštanciu
object RetrofitInstance {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    val api: TmdbApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApi::class.java)
    }

    // API kľúč dostupný cez BuildConfig
    val apiKey: String = BuildConfig.TMDB_API_KEY
}