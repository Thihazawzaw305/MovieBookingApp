package com.padcmyanmar.thiha.moviebookingapp.network.responses

import com.google.gson.annotations.SerializedName
import com.padcmyanmar.thiha.moviebookingapp.data.vos.PaymentVO


data class GetPaymentResponse (

    @SerializedName("data")
    val data : List<PaymentVO>?
)