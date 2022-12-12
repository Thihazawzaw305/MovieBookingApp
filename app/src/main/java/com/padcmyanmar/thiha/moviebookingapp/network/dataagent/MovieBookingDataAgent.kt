package com.padcmyanmar.thiha.moviebookingapp.network.dataagent

import com.padcmyanmar.thiha.moviebookingapp.data.vos.CastVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CinemaDataVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DataVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.MovieVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.*
import com.padcmyanmar.thiha.moviebookingapp.network.CheckOutRequest

interface MovieBookingDataAgent {

    fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        onSuccess: (Pair<DataVO, String>) -> Unit,
        onFailure: (String) -> Unit

    )

    fun userLogin(
        email: String,
        password: String,
        onSuccess: (Pair<DataVO, String>) -> Unit,
        onFailure: (String) -> Unit

    )

    fun getProfile(
        userToken: String,
        onSuccess: (DataVO) -> Unit,
        onFailure: (String) -> Unit
    )
    fun userLogOut(
        userToken: String,
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
        movieId:String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCast(
        movieId:String,
        onSuccess: (List<CastVO>) -> Unit,
        onFailure: (String) -> Unit
    )
    fun getCinemaDayTimeslots(
        userToken: String,
        movieId: String,
        date: String,
        onSuccess: (List<CinemaDataVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getMovieSeat(
        userToken: String,
        cinemaDayTimeslotId: String,
        bookDate: String,
        onSuccess: (List<MovieSeatVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getSnackList(
        userToken: String,
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getPaymentMethods(
        userToken: String,
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    )
    fun createNewCard(
        userToken: String,
        cardNumber: String,
        cardHolder: String,
        expDate: String,
        cvc: String,
        onSuccess: (List<CardVO>) -> Unit,
        onFailure: (String) -> Unit
    )
    fun checkOut(
        userToken: String,
        checkOutRequest: CheckOutRequest,
        onSuccess: (CheckOutOkVO) -> Unit,
        onFailure: (String) -> Unit
    )
}