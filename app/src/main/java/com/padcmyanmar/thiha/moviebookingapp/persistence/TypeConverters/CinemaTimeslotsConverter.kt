package com.padcmyanmar.thiha.moviebookingapp.persistence.TypeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CinemaTimeslotsVO

class CinemaTimeslotsConverter {

    @TypeConverter
    fun toString(cinemaTimeslotsList:List<CinemaTimeslotsVO>?): String{
        return Gson().toJson(cinemaTimeslotsList)
    }

    @TypeConverter
    fun toCinemaTimeslotVOList(cinemaTimeslotListJsonStr:String):List<CinemaTimeslotsVO>?{
        val cinemaTimeslotListType= object: TypeToken<List<CinemaTimeslotsVO>?>(){}.type
        return Gson().fromJson(cinemaTimeslotListJsonStr,cinemaTimeslotListType)
    }
}