package com.padcmyanmar.thiha.moviebookingapp.data.vos

import com.google.gson.annotations.SerializedName

data class CastVO(

    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("id")
    val id : Int,
    @SerializedName("known_for")
    val knownFor : List<MovieVO>?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("name")
    val name : String?,
    @SerializedName("profile_path")
    val profilePath: String?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("known_for_department")
    val knownForDepartment : String?,
    @SerializedName("original_ name")
    val originalName : String?,
    @SerializedName("cast_id")
    val castId : Int?,
    @SerializedName("character")
    val character : String?,
    @SerializedName("credit_id")
    val creditId : String?,
    @SerializedName("order")
    val order : Int?,
)