package com.padcmyanmar.thiha.moviebookingapp.network

import com.padcmyanmar.thiha.moviebookingapp.data.vos.MovieVO
import com.padcmyanmar.thiha.moviebookingapp.network.responses.GetCastResponse
import com.padcmyanmar.thiha.moviebookingapp.network.responses.MovieListResponse
import com.padcmyanmar.thiha.moviebookingapp.utils.*
import retrofit2.Call
import retrofit2.http.*

interface TheMovieBookingApi {



    @GET(API_GET_NOW_SHOWING)
    fun getNowShowingMovies(
        @Query(PARAM_API_KEY) apiKey:String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1,
    ): Call<MovieListResponse>



    @GET(API_GET_COMING_SOON_MOVIES)
    fun getComingSoonMovies(
        @Query(PARAM_API_KEY) apiKey:String = MOVIE_API_KEY,
        @Query(PARAM_PAGE) page: Int = 1,
    ): Call<MovieListResponse>



    @GET("$API_GET_MOVIE_DETAILS/{movie_id}")
    fun getMovieDetails(
        @Path("movie_id")movieId : String,
        @Query(PARAM_API_KEY) apiKey:String = MOVIE_API_KEY,
    ):Call<MovieVO>


    @GET("$API_GET_CAST/{movie_id}/credits")
    fun getCast(
        @Path("movie_id")movieId : String,
        @Query(PARAM_API_KEY) apiKey:String = MOVIE_API_KEY,
    ):Call<GetCastResponse>



}