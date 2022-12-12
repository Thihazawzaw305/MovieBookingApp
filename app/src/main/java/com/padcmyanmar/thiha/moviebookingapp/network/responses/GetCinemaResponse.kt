package com.padcmyanmar.thiha.moviebookingapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CinemaDataVO

data class GetCinemaResponse (

    @SerializedName("data")
    val data : List<CinemaDataVO>?
        )