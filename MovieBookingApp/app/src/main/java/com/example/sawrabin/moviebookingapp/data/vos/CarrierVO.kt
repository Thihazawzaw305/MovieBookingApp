package com.example.sawrabin.moviebookingapp.data.vos

import com.google.gson.annotations.SerializedName

data class CarrierVO(
    @SerializedName("movie_id")
    val movie_id: Int?=null,
    @SerializedName("name")
    val name: String?=null,
    @SerializedName("cinema_id")
    val cinemaId:Int?=null,
    @SerializedName("cinema_name")
    val cinemaName: String? = null,
    @SerializedName("book_date")
    val bookDate: String? =null,
    @SerializedName("timeslot")
    val timeslot:Int?=null,
    @SerializedName("timeslot_time")
    val timeslotTime:String?=null,
    @SerializedName("total_price")
    val totalPrice:Int?=null,
    @SerializedName("seat_number")
    val seatNumber:String?=null,
    @SerializedName("row")
    val row :String?=null,
    @SerializedName("snack")
    val snack:List<SnackPaymentVO>?=null
)
