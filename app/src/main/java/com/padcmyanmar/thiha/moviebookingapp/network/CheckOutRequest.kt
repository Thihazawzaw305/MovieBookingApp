package com.padcmyanmar.thiha.moviebookingapp.network

import com.google.gson.annotations.SerializedName
import com.padcmyanmar.thiha.moviebookingapp.data.vos.SnackVO

class CheckOutRequest (

    @SerializedName("cinema_day_timeslot_id")
    val cinemaDayTimeslotId:Int?,
    @SerializedName("row")
    val row:String?,
    @SerializedName("seat_number")
    val seatNumber:String?,
    @SerializedName("booking_date")
    val bookingDate :String?,
    @SerializedName("movie_id")
    val movieId:Int?,
    @SerializedName("card_id")
    val cardId:Int?,
    @SerializedName("cinema_id")
    val cinemaId:Int?,
    @SerializedName("snacks")
    val snacks:List<SnackVO>?
    )
