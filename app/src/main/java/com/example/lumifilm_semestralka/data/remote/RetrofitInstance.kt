package com.example.lumifilm_semestralka.data.remote

import androidx.compose.foundation.R
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import com.example.lumifilm_semestralka.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.ui.res.stringResource

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

    // API kľúč

    val apiKey: String = "abf950e397737e5ecda9abd14e0bd09d"

    //val apiKey: String = stringResource(R.string.a_apicko)
}