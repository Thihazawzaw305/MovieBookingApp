package com.padcmyanmar.thiha.moviebookingapp.persistence.TypeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padcmyanmar.thiha.moviebookingapp.data.vos.GenreVO

class GenereTypeConverter {

    @TypeConverter
    fun toString(genreList : List<GenreVO>?):String{
        return Gson().toJson(genreList)
    }

    @TypeConverter
    fun toGenreVO(genreListString: String): List<GenreVO>?{
        val genreListType = object : TypeToken<List<GenreVO>?>(){}.type
        return Gson().fromJson(genreListString,genreListType)
    }
}