package com.padcmyanmar.thiha.moviebookingapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CheckOutOkVO
import com.padcmyanmar.thiha.moviebookingapp.utils.RESPONSE_OK

class CheckOutResponse (
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: CheckOutOkVO?,
    )
{



    fun isRequestOk():Boolean{
        return code == RESPONSE_OK
    }
}
