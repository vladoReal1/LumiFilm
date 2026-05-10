package com.example.lumifilm_semestralka.data.remote

import com.example.lumifilm_semestralka.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDetailDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("genres") val genres: List<GenreDto>?
) {
    fun toMovie(): Movie = Movie(
        id = id,
        title = title ?: "",
        overview = overview ?: "",
        posterPath = posterPath,
        releaseDate = releaseDate ?: "",
        voteAverage = voteAverage ?: 0.0,
        genreIds = genres?.map { it.id } ?: emptyList()
    )
}

data class GenreDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)