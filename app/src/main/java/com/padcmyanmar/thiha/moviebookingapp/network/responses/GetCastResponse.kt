package com.padcmyanmar.thiha.moviebookingapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CastVO

data class GetCastResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("cast")
    val cast: List<CastVO>?,
    @SerializedName("crew")
    val crew: List<CastVO>?,
    )