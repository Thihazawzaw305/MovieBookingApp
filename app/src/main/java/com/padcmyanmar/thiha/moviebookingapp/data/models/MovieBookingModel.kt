package com.padcmyanmar.thiha.moviebookingapp.data.models

import com.padcmyanmar.thiha.moviebookingapp.data.vos.CastVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CinemaDataVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DataVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.MovieVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.*

interface MovieBookingModel {


    fun registerUser(
        name : String,
        email : String,
        phone : String,
        password : String,
        onSuccess : () -> Unit,
        onFailure: (String) -> Unit

    )
    fun userLogin(
        email : String,
        password : String,
        onSuccess : () -> Unit,
        onFailure: (String) -> Unit

    )
    fun getProfile(
        onSuccess: (DataVO) -> Unit,
        onFailure: (String) -> Unit
    )


    fun userLogOut(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )


    fun getNowShowingMovies(
        onSuccess : (List<MovieVO>) -> Unit,
        onFailure : (String) -> Unit
    )

    fun getComingSoonMovies(
        onSuccess : (List<MovieVO>) -> Unit,
        onFailure : (String) -> Unit
    )

    fun getMovieDetails(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    )


    fun getCast(
        movieId:String,
        onSuccess: (List<CastVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCinemaDayTimeslots(
        movieId: String,
        date: String,
        onSuccess: (List<CinemaDataVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMovieSeat(
        cinemaDayTimeslotId: String,
        bookDate: String,
        onSuccess: (List<MovieSeatVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSnackList(
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getPaymentMethods(
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun createNewCard(
        cardNumber: String,
        cardHolder: String,
        expDate: String,
        cvc: String,
        onSuccess: (List<CardVO>) -> Unit,
        onFailure: (String) -> Unit
    )
    fun checkOut(
//        cinemaDayTimeslotId: Int,
//        row: String,
//        seatNumber: String,
//        bookingDate: String,
//        movieId: Int,
        cardId: Int,
//        cinemaId: Int,
//        snacks: List<SnackVO>,
        onSuccess: (CheckOutOkVO) -> Unit,
        onFailure: (String) -> Unit
    )
}