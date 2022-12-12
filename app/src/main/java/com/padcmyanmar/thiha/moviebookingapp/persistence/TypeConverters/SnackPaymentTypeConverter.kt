package com.padcmyanmar.thiha.moviebookingapp.persistence.TypeConverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.padcmyanmar.thiha.moviebookingapp.data.vos.PaymentVO


class SnackPaymentTypeConverter {
    @TypeConverter
    fun toString(snackList: List<PaymentVO>?): String {
        return Gson().toJson(snackList)
    }

    @TypeConverter
    fun toSnackPaymentVOList(snackPaymentVOJsonStr: String): List<PaymentVO>? {
        val snackPaymentType = object : TypeToken<List<PaymentVO>?>() {}.type
        return Gson().fromJson(snackPaymentVOJsonStr, snackPaymentType)
    }
}