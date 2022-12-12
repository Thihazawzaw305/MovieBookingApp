package com.padcmyanmar.thiha.moviebookingapp.data.models

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CastVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CinemaDataVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DataVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.MovieVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.*
import com.padcmyanmar.thiha.moviebookingapp.network.CheckOutRequest
import com.padcmyanmar.thiha.moviebookingapp.network.dataagent.MovieBookingDataAgent
import com.padcmyanmar.thiha.moviebookingapp.network.dataagent.RetrofitDataAgentImpl
import com.padcmyanmar.thiha.moviebookingapp.persistence.MovieBookingDatabase

object MovieBookingModelImpl : MovieBookingModel{

    private  val mMovieBookingDataAgent : MovieBookingDataAgent = RetrofitDataAgentImpl
    var mMovieBookingDatabase: MovieBookingDatabase? = null
//    private  var userToken : String? = null



    fun initDatabase(context: Context) {
        mMovieBookingDatabase = MovieBookingDatabase.getInstance(context)
    }










    override fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.registerUser(
            name = name,
            email = email,
            phone = phone ,
            password = password,
            onSuccess =
            {

//                val token = it.second
//                this.userToken = token
                it.first.userToken = it.second
                mMovieBookingDatabase?.DataDao()?.insertUserData(it.first)
                onSuccess()
            }
            , onFailure = onFailure)
    }


    override fun userLogin(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mMovieBookingDataAgent.userLogin(
            email = email,
            password = password,
            onSuccess = {
//                this.userToken= it.second
                it.first.userToken = it.second
                mMovieBookingDatabase?.DataDao()?.insertUserData(it.first)
                onSuccess()
            },
            onFailure = onFailure
        )
    }

    override fun getProfile(onSuccess: (DataVO) -> Unit, onFailure: (String) -> Unit) {
//        mMovieBookingDataAgent.getProfile(
//            userToken = "Bearer $userToken",
//            onSuccess = onSuccess,
//            onFailure = onFailure
//        )

        val userdata = mMovieBookingDatabase?.DataDao()?.getUserData()
        userdata?.let {
            onSuccess(it)
        }
        userdata?.userToken?.getBearerToken()?.let {
            mMovieBookingDataAgent.getProfile(
                userToken = it,
                onSuccess = { DataVO ->
                    DataVO.userToken = userdata.userToken
                    mMovieBookingDatabase?.DataDao()?.insertUserData(DataVO)
                    onSuccess(
                        DataVO
                    )
                },
                onFailure = onFailure
            )
        }
    }

    override fun userLogOut(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        //       mMovieBookingDataAgent.userLogOut("Bearer $userToken", onSuccess = {
//            userToken = null
//            onSuccess()
//        }, onFailure = onFailure)

        val userString = mMovieBookingDatabase?.DataDao()?.getToken()
        Log.println(Log.INFO, "user", userString.toString())
        userString?.getBearerToken()?.let {
            mMovieBookingDataAgent.userLogOut(it, onSuccess = {
                mMovieBookingDatabase?.DataDao()?.updateUserLogout()
                onSuccess()
            }, onFailure = onFailure)
        }

    }

    override fun getNowShowingMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
//        mMovieBookingDataAgent.getNowShowingMovies(onSuccess = onSuccess, onFailure = onFailure)
        onSuccess(
            mMovieBookingDatabase?.moviesDao()?.getMoviesByType(type = NOW_SHOWING) ?: listOf()
        )
        mMovieBookingDataAgent.getNowShowingMovies(onSuccess = {
            it.forEach { movie ->
                movie.type = NOW_SHOWING
            }
            mMovieBookingDatabase?.moviesDao()?.insertMovies(it)
            onSuccess(it)
        }, onFailure = onFailure)
    }

    override fun getComingSoonMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
//        mMovieBookingDataAgent.getComingSoonMovies(onSuccess = onSuccess, onFailure = onFailure)
        onSuccess(
            mMovieBookingDatabase?.moviesDao()?.getMoviesByType(type = COMING_SOON) ?: listOf()
        )
        mMovieBookingDataAgent.getComingSoonMovies(onSuccess = {
            it.forEach { movie ->
                movie.type = COMING_SOON
            }
            mMovieBookingDatabase?.moviesDao()?.insertMovies(it)
            onSuccess(it)
        }, onFailure = onFailure)
    }

    override fun getMovieDetails(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
      //  mMovieBookingDataAgent.getMovieDetails(movieId = movieId,onSuccess = onSuccess, onFailure = onFailure)
        mMovieBookingDatabase?.moviesDao()?.getMovieByID(movieId.toInt())?.let { onSuccess(it) }
        mMovieBookingDataAgent.getMovieDetails(
            movieId = movieId,
            onSuccess = {
                val movie = mMovieBookingDatabase?.moviesDao()?.getMovieByID(movieId.toInt())
                it.type = movie?.type
                mMovieBookingDatabase?.moviesDao()?.insertSingleMovie(it)
                onSuccess(it)
            },
            onFailure = onFailure
        )
    }

    override fun getCast(movieId: String, onSuccess: (List<CastVO>) -> Unit, onFailure: (String) -> Unit) {
        mMovieBookingDataAgent.getCast(movieId = movieId,onSuccess = onSuccess, onFailure = onFailure)
    }
    override fun getCinemaDayTimeslots(
        movieId: String,
        date: String,
        onSuccess: (List<CinemaDataVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
//        mMovieBookingDataAgent.getCinemaDayTimeslots(
//            userToken = "Bearer $userToken",
//            movieId = movieId,
//            date = date,
//            onSuccess = onSuccess,
//            onFailure = onFailure
//        )

        mMovieBookingDatabase?.cinemaTimeSlotDao()?.getCinemaTimeslots(date)?.let { onSuccess(it) }

        mMovieBookingDatabase?.DataDao()?.getToken()?.let { userToken ->
            mMovieBookingDataAgent.getCinemaDayTimeslots(
                userToken = userToken.getBearerToken(),
                movieId = movieId,
                date = date,
                onSuccess = {
                    it.forEach { cinemaDayVO ->
                        cinemaDayVO.date = date
                    }
                    mMovieBookingDatabase?.cinemaTimeSlotDao()?.deleteCinemaTimeslots(date)
                    mMovieBookingDatabase?.cinemaTimeSlotDao()?.insertCinemaTimeSlots(it)
                    onSuccess(it)
                },
                onFailure = onFailure
            )
        }
    }

    override fun getMovieSeat(
        cinemaDayTimeslotId: String,
        bookDate: String,
        onSuccess: (List<MovieSeatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
//        mMovieBookingDataAgent.getMovieSeat(
//            userToken = "Bearer $userToken",
//            cinemaDayTimeslotId = cinemaDayTimeslotId,
//            bookDate = bookDate, onSuccess = onSuccess, onFailure = onFailure
//        )

        mMovieBookingDatabase?.DataDao()?.getToken()?.getBearerToken()?.let {
            mMovieBookingDataAgent.getMovieSeat(
                userToken = it,
                cinemaDayTimeslotId = cinemaDayTimeslotId,
                bookDate = bookDate, onSuccess = onSuccess, onFailure = onFailure
            )
        }
    }

    override fun getSnackList(
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
//        mMovieBookingDataAgent.getSnackList(
//            userToken = "Bearer $userToken",
//            onSuccess = onSuccess,
//            onFailure = onFailure
//        )
        mMovieBookingDatabase?.snacksDao()?.getSnacks()?.let { onSuccess(it) }
        mMovieBookingDatabase?.DataDao()?.getToken()?.getBearerToken()?.let {
            mMovieBookingDataAgent.getSnackList(
                userToken = it,
                onSuccess = { payments ->
                    mMovieBookingDatabase?.snacksDao()?.insertSnacks(payments)
                    onSuccess(payments)
                },
                onFailure = onFailure
            )
        }
    }

    override fun getPaymentMethods(
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
//        mMovieBookingDataAgent.getPaymentMethods(
//            userToken = "Bearer $userToken",
//            onSuccess = onSuccess,
//            onFailure = onFailure
//        )
        mMovieBookingDatabase?.paymentDao()?.getPayments()?.let { onSuccess(it) }
        mMovieBookingDatabase?.DataDao()?.getToken()?.getBearerToken()?.let {
            mMovieBookingDataAgent.getPaymentMethods(
                userToken = it,
                onSuccess = { payments ->
                    mMovieBookingDatabase?.paymentDao()?.insertPayments(payments)
                    onSuccess(payments)
                },
                onFailure = onFailure
            )
        }
    }

    override fun createNewCard(
        cardNumber: String,
        cardHolder: String,
        expDate: String,
        cvc: String,
        onSuccess: (List<CardVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
//        mMovieBookingDataAgent.createNewCard(
//            userToken= "Bearer $userToken",
//            cardNumber = cardNumber,
//            cardHolder = cardHolder,
//            expDate = expDate,
//            cvc = cvc,
//            onSuccess = onSuccess,
//            onFailure = onFailure
//        )
        mMovieBookingDatabase?.DataDao()?.getToken()?.getBearerToken()?.let {
            mMovieBookingDataAgent.createNewCard(
                userToken = it,
                cardNumber = cardNumber,
                cardHolder = cardHolder,
                expDate = expDate,
                cvc = cvc,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }
    }

    override fun checkOut(
//        cinemaDayTimeslotId: Int,
//        row: String,
//        seatNumber: String,
//        bookingDate: String,
 //       movieId: Int,
        cardId: Int,
//        cinemaId: Int,
//        snacks: List<SnackVO>,
        onSuccess: (CheckOutOkVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
//        val mCheckOutRequest= CheckOutRequest(cinemaDayTimeslotId,row,seatNumber,bookingDate, movieId, cardId, cinemaId, snacks)
//        mMovieBookingDataAgent.checkOut(
//            checkOutRequest = mCheckOutRequest,
//            userToken= "Bearer $userToken",
//            onSuccess = onSuccess,
//            onFailure = onFailure
//        )

        val mCarrierData = getBookingData()

        val mCheckOutRequest = CheckOutRequest(
            mCarrierData?.timeslot,
            mCarrierData?.row,
            mCarrierData?.seatNumber,
            mCarrierData?.bookDate,
            mCarrierData?.movie_id,
            cardId,
            mCarrierData?.cinemaId,
            mCarrierData?.snack
        )
        mMovieBookingDatabase?.DataDao()?.getToken()?.getBearerToken()?.let {
            mMovieBookingDataAgent.checkOut(
                checkOutRequest = mCheckOutRequest,
                userToken = it,
                onSuccess = onSuccess,
                onFailure = onFailure
            )
        }
    }




    private fun String.getBearerToken(): String {
        return "Bearer $this"
    }
    fun checkLogin(): Boolean {
        return when (mMovieBookingDatabase?.DataDao()?.getUserData()) {
            null -> false
            else -> true
        }

    }


    fun storeMovieDetailData(runtime: String, posterPath: String, name: String) {
        mMovieBookingDatabase?.dataCarrierDao()
            ?.updateBookingDataWithMovieDetail(runtime, posterPath, name)
    }

    fun insertMovieId(movieId: Int) {
        val mCarrierVO = DataCarrierVO(movie_id = movieId)
        mMovieBookingDatabase?.dataCarrierDao()?.insertCarrierData(mCarrierVO)
    }

    fun storeCinemaTimeSlotData(
        cinemaId: Int,
        cinema_name: String,
        bookDate: String,
        timeslot: Int,
        timeslot_time: String
    ) {
        mMovieBookingDatabase?.dataCarrierDao()?.updateBookingDataWithTimeSlot(
            cinemaId,
            cinema_name,
            bookDate,
            timeslot,
            timeslot_time
        )
    }

    fun storeBookingNo(bookingNo: String) {
        mMovieBookingDatabase?.dataCarrierDao()?.updateBookingDataWithBookingNo(bookingNo)
    }

    fun storeMovieSeatData(row: String, totalPrice: Int, seatNumber: String) {
        mMovieBookingDatabase?.dataCarrierDao()?.updateBookingDataWithTime(row, totalPrice, seatNumber)
    }

    fun storeSnackData(snack: List<SnackVO>?, totalPrice: Int) {
        val snackJson = Gson().toJson(snack)
        mMovieBookingDatabase?.dataCarrierDao()?.updateBookingDataWithSnack(snackJson, totalPrice)
    }

    fun getBookingData(): DataCarrierVO? {
        return mMovieBookingDatabase?.dataCarrierDao()?.getBookingData()
    }

}