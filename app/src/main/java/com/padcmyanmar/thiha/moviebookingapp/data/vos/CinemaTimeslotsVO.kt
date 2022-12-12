package com.padcmyanmar.thiha.moviebookingapp.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

data class CinemaTimeslotsVO(


    var isSelected:Boolean?,
    @SerializedName("cinema_day_timeslot_id")
    val cinemaDayTimeslotId: Int?,
    @SerializedName("start_time")
    val startTime: String?,

)

