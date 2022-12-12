package com.padcmyanmar.thiha.moviebookingapp.persistence.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padcmyanmar.thiha.moviebookingapp.data.vos.PaymentVO

@Dao
interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = PaymentVO::class)
    fun insertPayments(snack : List<PaymentVO>)

    @Query("SELECT * FROM payments")
    fun getPayments():List<PaymentVO>

}