package com.padcmyanmar.thiha.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.adapters.MovieSeatAdapter
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DataCarrierVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.MovieSeatVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.SEAT_TYPE_AVAILABLE
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModel
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModelImpl
import com.padcmyanmar.thiha.moviebookingapp.delegates.MovieSeatDelegate
import kotlinx.android.synthetic.main.activity_seat.*

class SeatActivity : AppCompatActivity(), MovieSeatDelegate {
    lateinit var mAdapter: MovieSeatAdapter
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl
    private var mCarrierData: DataCarrierVO? = null
    private var mMovieSeatList: List<MovieSeatVO>? = null
    private var mSelectedSeat: String = ""
    private var mNumberTicket: Int = 0
    private var mTotalPrice = 0
    private var mRow: String = ""
    companion object {
        const val EXTRA_CARRIER_DATA = "EXTRA_CARRIER_DATA"
        fun newIntent(context: Context, data: String): Intent {
            val intent = Intent(context, SeatActivity::class.java)
            intent.putExtra(EXTRA_CARRIER_DATA, data)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat)
        val mData = intent?.getStringExtra(EXTRA_CARRIER_DATA)
        mCarrierData = Gson().fromJson(mData, DataCarrierVO::class.java)
        Log.println(Log.INFO, "carrier_Seat", mCarrierData.toString())
        setUpBuyTicket()
        setUpSeatingPlan()
        setUpListener()
        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        mCarrierData?.let {
            requestData(it)
            setUpViewWithData(it)
        }
    }

    private fun setUpSeatingPlan() {
        mAdapter = MovieSeatAdapter(mDelegate = this)
        rvSeatingPlan.adapter = mAdapter
        rvSeatingPlan.layoutManager = GridLayoutManager(this, 14)

    }



    private fun requestData(carrier: DataCarrierVO) {
        mMovieBookingModel.getMovieSeat(
            bookDate = carrier.bookDate ?: "",
            cinemaDayTimeslotId = carrier.timeslot.toString(),
            onFailure = {
                showError(it)
            },
            onSuccess = {
                Log.println(Log.ERROR, "Seat", it.toString())
                mAdapter.setNewData(it)
                mMovieSeatList = it

            })
    }

// btn buy ticket and jsondatacarrier
    private fun setUpBuyTicket() {
        btnSeatBuyTicket.setOnClickListener {
//            startActivity(SnackActivity.newIntent(this))
            if (mNumberTicket != 0) {
                MovieBookingModelImpl.storeMovieSeatData(mRow, mTotalPrice, mSelectedSeat)
                mCarrierData?.let {
                    it.totalPrice = mTotalPrice
                    it.seatNumber = mSelectedSeat
                    it.row = mRow
                }

                val carrierDataJson = Gson().toJson(mCarrierData)
                startActivity(SnackActivity.newIntent(this, carrierDataJson))
                Log.println(Log.INFO, "movieTimeString", mCarrierData.toString())
            }else

                showError("Please Select Movie Seat")



    }}





    private fun setUpViewWithData(carrier: DataCarrierVO) {
        tvSeatScreenMovieTitle.text = carrier.name?:"need data"
        tvSeatScreenCinemaName.text = carrier.cinemaName?:"need data"
        "${carrier.bookDate}    ${carrier.timeslotTime}".also { tvSeatScreenDateAndTime.text = it }
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
            mAdapter.setNewData(mMovieSeatList ?: listOf())
            movieSeatDataUpdate()
        }


    }
    private fun movieSeatDataUpdate() {
        tvTicketCount.text = mNumberTicket.toString()
        "BUY TICKET FOR  $ $mTotalPrice".also { tvBuyTicketFor.text = it }
        val seatSelected = mMovieSeatList?.let { seatList ->
            seatList.filter { it.isSelected == true }
        }
        mSelectedSeat = seatSelected?.map {
            it.seatName
        }?.joinToString(",") ?: ""
        mRow = seatSelected?.map { it.symbol }?.distinct()?.joinToString(",") ?: ""
        tvSeatCount.text = mSelectedSeat
    }

    private fun setUpListener() {
        btnSeatBack.setOnClickListener {
            super.onBackPressed()
        }

    }

    private fun showError(error: String) {
        Snackbar.make(window.decorView, error, Snackbar.LENGTH_LONG).show()
    }
}