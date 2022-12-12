package com.padcmyanmar.thiha.moviebookingapp

import android.app.Application
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModelImpl

class MovieBookingApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        MovieBookingModelImpl.initDatabase(applicationContext)
    }
}