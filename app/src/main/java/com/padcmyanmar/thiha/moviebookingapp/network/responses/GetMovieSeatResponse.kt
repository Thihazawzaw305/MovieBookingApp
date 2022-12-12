package com.padcmyanmar.thiha.moviebookingapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padcmyanmar.thiha.moviebookingapp.data.vos.MovieSeatVO

data class GetMovieSeatResponse (
    @SerializedName("data")
    val data: List<List<MovieSeatVO>>?
) {

    fun flattenedList(): List<MovieSeatVO>? {
        return data?.flatten()
    }

}
