package com.padcmyanmar.thiha.moviebookingapp.network.dataagent

import android.util.Log
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CastVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CinemaDataVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DataVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.MovieVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.*
import com.padcmyanmar.thiha.moviebookingapp.network.CheckOutRequest
import com.padcmyanmar.thiha.moviebookingapp.network.TheMovieBookingApi
import com.padcmyanmar.thiha.moviebookingapp.network.TheMovieBookingApiForRegister
import com.padcmyanmar.thiha.moviebookingapp.network.responses.*
import com.padcmyanmar.thiha.moviebookingapp.utils.BASE_URL
import com.padcmyanmar.thiha.moviebookingapp.utils.REGISTER_URL
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitDataAgentImpl: MovieBookingDataAgent {

    private var mTheMovieBookingApi: TheMovieBookingApi? = null
    private var mTheMovieBookingApiForRegister: TheMovieBookingApiForRegister? = null

    init {
        val mOkHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mTheMovieBookingApi=retrofit.create(TheMovieBookingApi::class.java)

        val retrofitForRegister = Retrofit.Builder()
            .baseUrl(REGISTER_URL)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
      mTheMovieBookingApiForRegister=retrofitForRegister.create(TheMovieBookingApiForRegister::class.java)
    }


    override fun registerUser(
        name: String,
        email: String,
        phone: String,
        password: String,
        onSuccess: (Pair<DataVO,String>) -> Unit,
        onFailure: (String) -> Unit
    ) {
       mTheMovieBookingApiForRegister?.registerUser(name =name,
            email= email,
            phone =phone,
            password = password)
            ?.enqueue(object : Callback<RegisterLoginProfileResponse>{
                override fun onResponse(
                    call: Call<RegisterLoginProfileResponse>,
                    response: Response<RegisterLoginProfileResponse>
                ) {
                    if (response.isSuccessful){

                        response.body()?.let {
                            it.Data?.let { DataVo ->
                                onSuccess(Pair(DataVo, it.token ?: ""))
                            }


                        }

                    }

                    else{
                        onFailure(response.message())

                    }
                }

                override fun onFailure(call: Call<RegisterLoginProfileResponse>, t: Throwable) {
                    onFailure(t.message?:"Error Message")

                }
            })
    }

    override fun userLogin(
        email: String,
        password: String,
        onSuccess: (Pair<DataVO,String>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApiForRegister?.userLogin(
            email= email,
            password = password)
            ?.enqueue(object : Callback<RegisterLoginProfileResponse>{
                override fun onResponse(
                    call: Call<RegisterLoginProfileResponse>,
                    response: Response<RegisterLoginProfileResponse>
                ) {
                    if (response.isSuccessful){
                        response.body()?.let {
                            if (it.isRequestOk()) {
                                it.Data?.let { DataVo ->
                                    onSuccess(Pair(DataVo, it.token ?: ""))
                                }


                            }


                        }}

                    else{
                        onFailure(response.message())


                    }
                }

                override fun onFailure(call: Call<RegisterLoginProfileResponse>, t: Throwable) {
                    onFailure(t.message?:"Error Message")

                }
            })
    }
    override fun userLogOut(userToken: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        mTheMovieBookingApiForRegister?.userLogOut(userToken)?.enqueue(object : Callback<RegisterLoginProfileResponse> {
            override fun onResponse(
                call: Call<RegisterLoginProfileResponse>,
                response: Response<RegisterLoginProfileResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.isRequestOk()) {
                            onSuccess()
                        }
                    }
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<RegisterLoginProfileResponse>, t: Throwable) {
                onFailure(t.message ?: "Error message")
            }

        })
    }
    override fun getProfile(
        userToken: String,
        onSuccess: (DataVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApiForRegister?.getProfile(userToken)?.enqueue(object : Callback<RegisterLoginProfileResponse> {
            override fun onResponse(
                call: Call<RegisterLoginProfileResponse>,
                response: Response<RegisterLoginProfileResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        it.Data?.let { dataVO ->
                            onSuccess(dataVO)
                        }
                    }
                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<RegisterLoginProfileResponse>, t: Throwable) {
                onFailure(t.message ?: "Error message")
            }

        })
    }


    override fun getNowShowingMovies(
        onSuccess : (List<MovieVO>) -> Unit,
        onFailure : (String) -> Unit
    ) {
        mTheMovieBookingApi?.getNowShowingMovies()
            ?.enqueue(object : Callback<MovieListResponse> {
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    if (response.isSuccessful){
                        val movieList = response.body()?.results?: listOf()
                        onSuccess(movieList)
                    }

                    else{
                        onFailure(response.message())

                    }
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    onFailure(t.message?:"Error message")

                }
            })
    }

    override fun getComingSoonMovies(
        onSuccess: (List<MovieVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getComingSoonMovies()
            ?.enqueue(object : Callback<MovieListResponse> {
                override fun onResponse(
                    call: Call<MovieListResponse>,
                    response: Response<MovieListResponse>
                ) {
                    if (response.isSuccessful){
                        val movieList = response.body()?.results?: listOf()
                        onSuccess(movieList)
                    }

                    else{
                        onFailure(response.message())

                    }
                }

                override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                    onFailure(t.message?:"Error message")

                }
            })
    }

    override fun getMovieDetails(
        movieId: String,
        onSuccess: (MovieVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApi?.getMovieDetails(movieId = movieId)
            ?.enqueue(object : Callback<MovieVO> {
                override fun onResponse(
                    call: Call<MovieVO>,
                    response: Response<MovieVO>
                ) {
                    if (response.isSuccessful) {

                        response.body()?.let {
                            onSuccess(it)
                        }
                    } else {
                        onFailure(response.message())

                    }
                }

                override fun onFailure(call: Call<MovieVO>, t: Throwable) {
                    onFailure(t.message ?: "Error message")
                }
            })
    }



    override fun getCast(movieId :String, onSuccess: (List<CastVO>) -> Unit, onFailure: (String) -> Unit) {
        mTheMovieBookingApi?.getCast(movieId = movieId)
            ?.enqueue(object : Callback<GetCastResponse>{
                override fun onResponse(
                    call: Call<GetCastResponse>,
                    response: Response<GetCastResponse>
                ) {
                    if (response.isSuccessful){

                            val cast = response.body()?.cast?: listOf()
                            onSuccess(cast)


                    }

                    else{
                        onFailure(response.message())

                    }
                }

                override fun onFailure(call: Call<GetCastResponse>, t: Throwable) {
                    onFailure(t.message?:"Error message")

                }
            })
    }

    override fun getCinemaDayTimeslots(
        userToken: String,
        movieId: String,
        date: String,
        onSuccess: (List<CinemaDataVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApiForRegister?.getCinemaDayTimeSlots(
            authorization = userToken,
            movieId = movieId,
            date = date
        )?.enqueue(
            object : Callback<GetCinemaResponse> {
                override fun onResponse(
                    call: Call<GetCinemaResponse>,
                    response: Response<GetCinemaResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Log.println(Log.ERROR, "slot", it.toString())
                            onSuccess(it.data ?: listOf())
                        }
                    } else onFailure(response.message())

                }

                override fun onFailure(call: Call<GetCinemaResponse>, t: Throwable) {
                    onFailure(t.message ?: "Error message")
                }

            }
        )
    }

    override fun getMovieSeat(
        userToken: String,
        cinemaDayTimeslotId: String,
        bookDate: String,
        onSuccess: (List<MovieSeatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApiForRegister?.getSeatingPlan(
            authorization = userToken,
            cinemaTimeDaySlotId = cinemaDayTimeslotId,
            bookingDate = bookDate
        )?.enqueue(object : Callback<GetMovieSeatResponse> {
            override fun onResponse(
                call: Call<GetMovieSeatResponse>,
                response: Response<GetMovieSeatResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.println(Log.ERROR, "response", it.toString())
                        val seatingList = it.flattenedList() ?: listOf()

                        Log.println(Log.ERROR, "flatten", seatingList.toString())

                        onSuccess(seatingList)
                    }

                } else onFailure(response.message())
            }

            override fun onFailure(call: Call<GetMovieSeatResponse>, t: Throwable) {
                onFailure(t.message ?: "")
            }

        })
    }

    override fun getSnackList(
        userToken: String,
        onSuccess: (List<SnackVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApiForRegister?.getSnackList(userToken)
            ?.enqueue(object : Callback<GetSnackResponse> {
                override fun onResponse(
                    call: Call<GetSnackResponse>,
                    response: Response<GetSnackResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onSuccess(it.data ?: listOf())
                        }
                    } else onFailure(response.message())
                }

                override fun onFailure(call: Call<GetSnackResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun getPaymentMethods(
        userToken: String,
        onSuccess: (List<PaymentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApiForRegister?.getPaymentMethods(userToken)
            ?.enqueue(object : Callback<GetPaymentResponse> {
                override fun onResponse(
                    call: Call<GetPaymentResponse>,
                    response: Response<GetPaymentResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onSuccess(it.data ?: listOf())
                        }
                    } else onFailure(response.message())
                }

                override fun onFailure(call: Call<GetPaymentResponse>, t: Throwable) {
                    onFailure(t.message ?: "")
                }

            })
    }

    override fun createNewCard(
        userToken: String,
        cardNumber: String,
        cardHolder: String,
        expDate: String,
        cvc: String,
        onSuccess: (List<CardVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApiForRegister?.createNewCard(
            authorization = userToken,
            cardNumber = cardNumber,
            cardHolder = cardHolder,
            ExpDate = expDate,
            cvc = cvc
        )?.enqueue(object : Callback<CreateNewCardResponse> {
            override fun onResponse(
                call: Call<CreateNewCardResponse>,
                response: Response<CreateNewCardResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.isRequestOk()) {
                            onSuccess(it.data ?: listOf())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<CreateNewCardResponse>, t: Throwable) {

            }

        })
    }

    override fun checkOut(
        userToken: String,
        checkOutRequest: CheckOutRequest,
        onSuccess: (CheckOutOkVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mTheMovieBookingApiForRegister?.checkOut(checkOutRequest = checkOutRequest, authorization = userToken)
            ?.enqueue(object : Callback<CheckOutResponse> {
                override fun onResponse(
                    call: Call<CheckOutResponse>,
                    response: Response<CheckOutResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (it.isRequestOk()) {
                                it.data?.let { checkOutSuccess ->
                                    onSuccess(checkOutSuccess)
                                }
                            } else onFailure(response.message())
                        }
                    }

                }

                override fun onFailure(call: Call<CheckOutResponse>, t: Throwable) {
                    Log.println(Log.INFO, "checkOutFail", t.message ?: "")
                }

    })


}}