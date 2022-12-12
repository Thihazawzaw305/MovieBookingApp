package com.padcmyanmar.thiha.moviebookingapp.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.padcmyanmar.thiha.moviebookingapp.data.vos.*
import com.padcmyanmar.thiha.moviebookingapp.persistence.Daos.*

@Database(
    entities = [DataVO::class, MovieVO::class, DataCarrierVO::class,SnackVO::class,PaymentVO::class,CinemaDataVO::class],
    exportSchema = false,
    version = 5
)
abstract class MovieBookingDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "MOVIE_BOOKING_DB"
        var dbInstance: MovieBookingDatabase? = null

        fun getInstance(context: Context): MovieBookingDatabase? {
            when (dbInstance) {
                null -> {
                    dbInstance =
                        Room.databaseBuilder(context, MovieBookingDatabase::class.java, DB_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return dbInstance
        }

    }

    abstract fun DataDao(): DataDao
    abstract fun moviesDao(): MoviesDao
    abstract fun dataCarrierDao(): DataCarrierDao
    abstract fun snacksDao(): SnackDao
    abstract fun paymentDao(): PaymentDao
    abstract fun cinemaTimeSlotDao(): CinemaTimeSlotDao
}