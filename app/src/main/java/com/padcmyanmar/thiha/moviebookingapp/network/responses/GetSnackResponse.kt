package com.padcmyanmar.thiha.moviebookingapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padcmyanmar.thiha.moviebookingapp.data.vos.SnackVO

data class GetSnackResponse(

    @SerializedName("data")
    val data : List<SnackVO>?

)