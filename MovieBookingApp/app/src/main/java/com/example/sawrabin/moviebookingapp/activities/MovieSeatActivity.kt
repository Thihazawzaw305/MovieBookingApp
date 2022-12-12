package com.example.sawrabin.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.adapter.MovieSeatAdapter
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModel
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import com.example.sawrabin.moviebookingapp.data.vos.CarrierVO
import com.example.sawrabin.moviebookingapp.data.vos.MovieSeatVO
import com.example.sawrabin.moviebookingapp.data.vos.SEAT_TYPE_AVAILABLE
import com.example.sawrabin.moviebookingapp.delegate.MovieSeatDelegate
import com.example.sawrabin.moviebookingapp.network.MovieBookingApi
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie_seat_activity.*
import java.text.SimpleDateFormat

class MovieSeatActivity : AppCompatActivity(), MovieSeatDelegate {
    lateinit var mMovieSeatAdapter: MovieSeatAdapter
    private var mMovieBooking: MovieBookingModel = MovieBookingModelImpl
    private var mCarrierData: CarrierVO? = null
    private var mMovieSeatList: List<MovieSeatVO>? = null
    private var mSelectedSeat: String = ""
    private var mNumberTicket: Int = 0
    private var mTotalPrice = 0

    companion object {
        const val EXTRA_CARRIER_DATA = "EXTRA_CARRIER_DATA"
        fun newIntent(context: Context, data: String): Intent {

            val intent = Intent(context, MovieSeatActivity::class.java)
            intent.putExtra(EXTRA_CARRIER_DATA, data)
            return intent
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_seat_activity)
        val mData = intent?.getStringExtra(EXTRA_CARRIER_DATA)
        mCarrierData = Gson().fromJson(mData, CarrierVO::class.java)
        Log.println(Log.INFO, "carrier_Seat", mCarrierData.toString())
        setUpSeatingPlanRecyclerView()
        setUpOnClickListener()

        mCarrierData?.let {
            requestData(it)
            setUpViewWithData(it)
        }


    }

    private fun setUpViewWithData(carrier: CarrierVO) {
        tvMovieSeatTitle.text = carrier.name
        tvMovieSeatCinema.text = carrier.cinemaName
        "${carrier.bookDate}    ${carrier.timeslotTime}".also { tvMovieSeatDate.text = it }
    }

    private fun requestData(carrier: CarrierVO) {
        mMovieBooking.getMovieSeat(
            bookDate = carrier.bookDate ?: "",
            cinemaDayTimeslotId = carrier.timeslot.toString(),
            onFailure = {
                showError(it)
            },
            onSuccess = {
                Log.println(Log.ERROR, "Seat", it.toString())
                mMovieSeatAdapter.setNewData(it)
                mMovieSeatList = it

            })
    }

    private fun setUpOnClickListener() {
//        ivBtnBackSeatPlan.setOnClickListener {
//            startActivity(MovieTimeActivity.newIntent(this))
//        }

        tvBuyTicket.setOnClickListener {
            if (mNumberTicket != 0) {

                val carrier = CarrierVO(
                    totalPrice = mTotalPrice,
                    seatNumber = mSelectedSeat,
                    movie_id = mCarrierData?.movie_id,
                    timeslot = mCarrierData?.timeslot,
                    name = mCarrierData?.name,
                    bookDate = mCarrierData?.bookDate,
                    cinemaName = mCarrierData?.cinemaName,
                    timeslotTime = mCarrierData?.timeslotTime,
                    cinemaId = mCarrierData?.cinemaId,


                    )
                val carrierDataJson = Gson().toJson(carrier)
                startActivity(SnackActivity.newIntent(this, carrierDataJson))
            } else showError("Please Select Movie Seat")

        }
    }

    private fun setUpSeatingPlanRecyclerView() {
        mMovieSeatAdapter = MovieSeatAdapter(mDelegate = this)
        rvSeatPlan.adapter = mMovieSeatAdapter
        rvSeatPlan.layoutManager = GridLayoutManager(this, 14)

    }

    override fun onTapMovieSeat(seatName: String) {
        for (seat in mMovieSeatList ?: listOf()) {
            if (seat.seatName == seatName) {
                if (seat.type == SEAT_TYPE_AVAILABLE) {
                    if (seat.isSelected == true) {
                        mNumberTicket--
                        seat.isSelected = false
                        mTotalPrice -= seat.price ?: 0

                    } else {
                        seat.isSelected = true
                        mNumberTicket++
                        mTotalPrice += seat.price ?: 0
                    }
                }
            }
        }
        mMovieSeatAdapter.setNewData(mMovieSeatList ?: listOf())
        tvNumberOfTickets.text = mNumberTicket.toString()
        "BUY TICKET FOR  $ $mTotalPrice".also { tvBuyTicket.text = it }
        mSelectedSeat = mMovieSeatList?.let { seatList ->
            seatList.filter { it.isSelected == true }.map {
                it.seatName
            }.joinToString(",") ?: ""
        }.toString()

        tvSeatNumbers.text = mSelectedSeat


    }

    private fun showError(error: String) {
        Snackbar.make(window.decorView, error, Snackbar.LENGTH_LONG).show()
    }
}