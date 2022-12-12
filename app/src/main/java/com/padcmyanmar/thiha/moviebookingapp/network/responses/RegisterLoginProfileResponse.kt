package com.padcmyanmar.thiha.moviebookingapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DataVO
import com.padcmyanmar.thiha.moviebookingapp.utils.RESPONSE_OK

data class RegisterLoginProfileResponse
    (
    @SerializedName("code")
    val code: Int ?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val Data: DataVO?,

    @SerializedName("token")
    val token: String?,



    )
{

    fun isRequestOk():Boolean{
        return code ==RESPONSE_OK
    }

}