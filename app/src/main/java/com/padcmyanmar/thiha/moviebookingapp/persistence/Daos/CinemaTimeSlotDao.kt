package com.padcmyanmar.thiha.moviebookingapp.persistence.Daos

import androidx.room.*
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CinemaDataVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CinemaTimeslotsVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DataCarrierVO


@Dao
interface CinemaTimeSlotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE ,entity =  CinemaDataVO::class)
    fun insertCinemaTimeSlots (cinemaTimeslotsList :List<CinemaDataVO>)

    @Query("SELECT * FROM cinema_timeslots WHERE date = :date")
    fun getCinemaTimeslots(date: String):List<CinemaDataVO>

    @Query("DELETE  FROM cinema_timeslots WHERE date = :date")
    fun deleteCinemaTimeslots (date :String)
}