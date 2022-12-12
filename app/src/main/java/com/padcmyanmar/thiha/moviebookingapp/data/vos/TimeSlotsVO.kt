package com.padcmyanmar.thiha.moviebookingapp.data.vos

import com.google.gson.annotations.SerializedName

data class TimeSlotsVO(

    var isSelected:Boolean?,
    @SerializedName("cinema_day_timeslot_id")
    val cinemaDayTimeslotId: Int?,
    @SerializedName("start_time")
    val startTime: String?,

)