package com.example.lumifilm_semestralka.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// AI assisted: Retrofit rozhranie pre TMDB API volania
interface TmdbApi {

    // Vyhľadávanie filmov
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "sk-SK",
        @Query("page") page: Int = 1
    ): MovieResponse

    // Filmy podľa žánru (pre odporúčania)
    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int,
        @Query("language") language: String = "sk-SK",
        @Query("page") page: Int = 1
    ): MovieResponse

    // Populárne filmy
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "sk-SK"
    ): MovieResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "sk-SK"
    ): MovieDetailDto
}