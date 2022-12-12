package com.padcmyanmar.thiha.moviebookingapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DateVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.MovieVO

 data class MovieListResponse (
     @SerializedName("page")
    val page: Int?,
     @SerializedName("results")
    val results : List<MovieVO>



)