package com.example.sawrabin.moviebookingapp.network.dataagents

import com.example.sawrabin.moviebookingapp.data.vos.*
import com.example.sawrabin.moviebookingapp.network.responses.GetCinemaTImeResponse

interface MovieBookingDataAgent {

    fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        onSuccess: (Pair<DataVO, String>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun emailLoginUser(
        email: String,
        password: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun userLogOut(
        userToken: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )

    fun getProfile(
        userToken: String,
        onSuccess: (DataVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getNowShowing(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit

    )

    fun getUpcoming(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit

    )

    fun getMovieDetail(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCredits(
        movieId: String,
        onSuccess: (List<ActorVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getCinemaDayTimeslots(
        userToken: String,
        movieId: String,
        date: String,
        onSuccess: (List<CinemaDayVO>) -> Unit,
        onFailure: (String) -> Unit
    )


    fun getMovieSeat(
        userToken: String,
        cinemaDayTimeslotId: String,
        bookDate: String,
        onSuccess: (List<MovieSeatVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getImdbRating(
        imdbId: String,
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    )


    fun getSnackList(
        userToken: String,
        onSuccess: (List<SnackPaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getPaymentMethods(
        userToken: String,
        onSuccess: (List<SnackPaymentVO>) -> Unit,
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

}