package com.padcmyanmar.thiha.moviebookingapp.persistence.TypeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CardVO

class CardTypeConverter {
    @TypeConverter

    fun toString(cardList : List<CardVO>?) :String{
        return Gson().toJson(cardList)
    }

    @TypeConverter
    fun toCardVO(cardListJsonString : String): List<CardVO>?{
        val cardListType = object :TypeToken<List<CardVO>?>(){} .type
        return Gson().fromJson(cardListJsonString,cardListType)
    }
}