package com.padcmyanmar.thiha.moviebookingapp.persistence.TypeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padcmyanmar.thiha.moviebookingapp.data.vos.SnackVO

class SnackTypeConverter {

    @TypeConverter
    fun toString(snackList: List<SnackVO>?): String {
        return Gson().toJson(snackList)
    }

    @TypeConverter
    fun toSnackPaymentVOList(snackPaymentVOJsonStr: String): List<SnackVO>? {
        val snackPaymentType = object : TypeToken<List<SnackVO>?>() {}.type
        return Gson().fromJson(snackPaymentVOJsonStr, snackPaymentType)
    }
}