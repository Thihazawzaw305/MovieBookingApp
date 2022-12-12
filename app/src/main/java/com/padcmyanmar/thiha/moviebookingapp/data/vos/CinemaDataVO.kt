package com.padcmyanmar.thiha.moviebookingapp.data.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.padcmyanmar.thiha.moviebookingapp.persistence.TypeConverters.CinemaTimeslotsConverter

@Entity(tableName = "cinema_timeslots")
@TypeConverters(
    CinemaTimeslotsConverter::class
)
data class CinemaDataVO (
    @PrimaryKey
    val id: Int?,
    @ColumnInfo(name = "date")
    var date : String?,
    @SerializedName("cinema_id")
    @ColumnInfo(name = "cinema_id")
    val cinemaId:Int?,
    @SerializedName("cinema")
    @ColumnInfo(name = "cinema")
    val cinema:String?,
    @SerializedName("timeslots")
    @ColumnInfo(name = "timeslots")
    val timeslots:List<CinemaTimeslotsVO>?

        )




