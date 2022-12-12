package com.padcmyanmar.thiha.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.adapters.CinemaAdapter
import com.padcmyanmar.thiha.moviebookingapp.adapters.DateAdapter
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CinemaDataVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DataCarrierVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DateVO
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModel
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModelImpl
import com.padcmyanmar.thiha.moviebookingapp.delegates.CinemaDateDelegate
import com.padcmyanmar.thiha.moviebookingapp.delegates.CinemaTimeslotDelegate
import kotlinx.android.synthetic.main.activity_get_ticket.*
import java.text.SimpleDateFormat
import java.util.*


class GetTicketActivity : AppCompatActivity(),CinemaDateDelegate ,CinemaTimeslotDelegate{
    private val mMovieBookingModel : MovieBookingModel = MovieBookingModelImpl

    private var mCinemaDayList: List<CinemaDataVO> = listOf()
    private var mDateList: List<DateVO> = listOf()
    private var mDataParsed: DataCarrierVO? = null
    private var mMovieId: Int? = null
    private var mSelectedTimeSlot: Int = 0
    private var mBookDate: String = ""
    private var carrierString: DataCarrierVO? = null
    private var mCinemaName: String? = ""
    private var mStartTime: String? = ""
    private var mTimeSlot: Int? = null
    private var isSelectedTimeSlot: Boolean = false
    private var mCinemaId: Int = 0
    companion object {
        const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        fun newIntent(context: Context, data: String): Intent {
            val intent = Intent(context, GetTicketActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, data)
            return intent

        }
    }
   private lateinit var mCinemaAdapter: CinemaAdapter
   private lateinit var mDateAdapter: DateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_ticket)
        setUpListener()
        generateTwoWeeks()
       setUpCinemaRecyclerView()
        setUpDateRecyclerView()
        getDataFromIntent()
        todayDateSelect()
        setUpOnClickListener()

    }

    private fun todayDateSelect() {
        val mToday = mDateList.firstOrNull()?.dayFull
        onTapCinemaDate(mToday ?: "")
    }

    private fun getDataFromIntent() {
      //  val mData = intent?.getStringExtra(EXTRA_MOVIE_ID)
        mDataParsed =MovieBookingModelImpl.getBookingData()
   //     mDataParsed = Gson().fromJson(mData, DataCarrierVO::class.java)
    }

    private fun setUpOnClickListener() {
        btnNext.setOnClickListener {
            MovieBookingModelImpl.storeCinemaTimeSlotData(mCinemaId,mCinemaName?:"",mBookDate,mTimeSlot?:0,mStartTime?:"")
            mDataParsed?.let {
                it.timeslot=mTimeSlot
                it.bookDate=mBookDate
                it.cinemaId=mCinemaId
                it.cinemaName=mCinemaName
                it.timeslotTime=mStartTime

            }
            if (isSelectedTimeSlot) {
                val carrierDataJson = Gson().toJson(mDataParsed)
                startActivity(SeatActivity.newIntent(this, carrierDataJson))
                Log.println(Log.INFO,"movieTimeString",mDataParsed.toString())
            } else showError("Please Select Movie Show Time")

        }

        }




    private fun setUpListener(){
        btnTicketScreenBack.setOnClickListener{
            super.onBackPressed()
        }
    }


    private fun setUpDateRecyclerView() {
        mDateAdapter = DateAdapter(this)
        rvDatelist.adapter = mDateAdapter
        rvDatelist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mDateAdapter.setNewData(mDateList)
    }


    private fun  setUpCinemaRecyclerView(){

        mCinemaAdapter = CinemaAdapter(this)
        rvCinemalist.adapter =mCinemaAdapter
        rvCinemalist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

    }


    private fun showError(error: String) {
        Snackbar.make(window.decorView, error, Snackbar.LENGTH_LONG).show()
    }
    private fun generateTwoWeeks() {
        val twoWeeksData = mutableListOf<DateVO>()
        for (day in 0..14) {
            val c1 = Calendar.getInstance()
            c1.add(Calendar.DATE, day)
            val time = c1.time
            val day1 = SimpleDateFormat("EEE", Locale.ENGLISH).format(time)
            val date = SimpleDateFormat("dd", Locale.ENGLISH).format(time)
            val dateFull = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(time)
            twoWeeksData.add(
                DateVO(
                    cinemaDay = date, cinemaDate = day1, dayFull = dateFull
                )
            )


        }
        mDateList = twoWeeksData
        Log.println(Log.INFO, "generatedDate", mDateList.toString())


    }

    override fun onTapTimeSlot(timeslotId: Int) {
        //showError(timeslotId.toString())
        mSelectedTimeSlot = timeslotId
        for (cinemaDay in mCinemaDayList) {
            for (timeslot in cinemaDay.timeslots ?: listOf()) {
                if (timeslot.cinemaDayTimeslotId == timeslotId) {
                    timeslot.isSelected = true
                    mCinemaId = cinemaDay.cinemaId ?: 0
                    mCinemaName = cinemaDay.cinema ?: ""
                    mStartTime = timeslot.startTime ?: ""
                    mTimeSlot = timeslot.cinemaDayTimeslotId
                    isSelectedTimeSlot = true
                    Log.println(Log.INFO, "c1", carrierString.toString())
                } else timeslot.isSelected = false
            }
        }
        mCinemaAdapter.setNewData(mCinemaDayList)
    }
    override fun onTapCinemaDate(dateSelected: String) {
        mBookDate = dateSelected
        isSelectedTimeSlot = false

        // Update UI
        for (dateList in mDateList) {
            dateList.isChosed = dateList.dayFull == dateSelected
        }
        mDateAdapter.setNewData(mDateList)
        //     Time slot network call
        mMovieBookingModel.getCinemaDayTimeslots(
            movieId = mMovieId.toString(), date = dateSelected, onSuccess = { dayList ->
                mCinemaAdapter.setNewData(dayList)
                mCinemaDayList = dayList

            }, onFailure = {
                showError(it)
            }
        )

    }
}