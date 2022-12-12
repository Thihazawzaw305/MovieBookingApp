package com.padcmyanmar.thiha.moviebookingapp.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.padcmyanmar.thiha.moviebookingapp.persistence.TypeConverters.GenereTypeConverter


@Entity(tableName = "movies")
@TypeConverters(
    GenereTypeConverter::class
)
data class MovieVO(

    @SerializedName("adult")
    @ColumnInfo(name = "adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    val backDropPath: String?,

//    @SerializedName("genre_ids")
//    @ColumnInfo(name = "genre_ids")
//    val genreIds: List<Int>?,
    @SerializedName("budget")
    @ColumnInfo(name = "budget")
    val budget: Int?,
    @SerializedName("genres")
    @ColumnInfo(name = "genres")
    val genres: List<GenreVO>?,
    @SerializedName("homepage")
    @ColumnInfo(name = "homepage")
    val homepage: String?,
    @SerializedName("id")
    @PrimaryKey
    val id: Int = 0,
    @SerializedName("imdb_id")
    @ColumnInfo(name = "imdb_id")
    val imdbId: String?,
    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    val overview: String?,
    @SerializedName("popularity")
    @ColumnInfo(name = "popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @SerializedName("media_type")
    @ColumnInfo(name = "media_type")
    val media_type: String?,
    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    val releaseDate: String?,
    @SerializedName("runtime")
    @ColumnInfo(name = "runtime")
    val runtime: Int?,
    @SerializedName("tagline")
    @ColumnInfo(name = "tagline")
    val tagline: String?,
    @SerializedName("title")
    @ColumnInfo(name = "title")
    val title: String?,
    @SerializedName("video")
    @ColumnInfo(name = "video")
    val video: Boolean?,
    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    val voteCount: Int?,
    @ColumnInfo(name = "imdb_rating")
    var imdbRating: Double?,
    @ColumnInfo(name = "type")
    var type: String?,
)


{

    fun getRatingBaseOnFiveStars(): Float {
        return voteAverage?.div(2)?.toFloat() ?: 0.0f
    }

    fun calculateRunTime(): String {
        val movieRunTime: String
        val hours: String = runtime?.div(60).toString()
        val min: String = runtime?.rem(60).toString()
        movieRunTime = "$hours h $min min"
        return movieRunTime


    }
}

const val NOW_SHOWING = "now showing"
const val COMING_SOON = "coming soon"

