package com.padcmyanmar.thiha.moviebookingapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CardVO
import com.padcmyanmar.thiha.moviebookingapp.utils.RESPONSE_OK

data class CreateNewCardResponse (

    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: List<CardVO>?

        )


{



        fun isRequestOk():Boolean{
            return code == RESPONSE_OK
        }
}