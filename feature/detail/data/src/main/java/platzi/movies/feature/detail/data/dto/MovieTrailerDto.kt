package platzi.movies.feature.detail.data.dto

import com.google.gson.annotations.SerializedName

data class MovieTrailerDto(
    @SerializedName("official") val isOfficial: Boolean?,
    @SerializedName("key") val key: String?,
)