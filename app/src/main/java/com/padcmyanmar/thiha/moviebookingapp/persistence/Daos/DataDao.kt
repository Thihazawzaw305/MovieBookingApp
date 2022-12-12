package com.padcmyanmar.thiha.moviebookingapp.persistence.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DataVO


@Dao
 interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserData(user: DataVO)

    @Query("SELECT userToken FROM userData WHERE userToken Not Null")
    fun getToken(): String?

    @Query("SELECT * FROM userData WHERE userToken NOT NUll")
    fun getUserData(): DataVO?

    @Query("UPDATE userData SET userToken = NULL WHERE userToken Not Null")
    fun updateUserLogout()

}