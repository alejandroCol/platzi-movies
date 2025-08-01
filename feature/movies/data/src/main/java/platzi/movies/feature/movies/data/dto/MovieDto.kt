package platzi.movies.feature.movies.data.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val rating: Double
)