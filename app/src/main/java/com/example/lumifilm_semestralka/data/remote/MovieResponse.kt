package com.example.lumifilm_semestralka.data.remote

import com.example.lumifilm_semestralka.domain.model.Movie
import com.google.gson.annotations.SerializedName

// AI assisted: DTO triedy pre odpovede z TMDB API
data class MovieResponse(
    @SerializedName("results")
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

data class MovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("genre_ids")
    val genreIds: List<Int>
)

{
    fun toMovie(): Movie { return TODO("Provide the return value")
    }
}