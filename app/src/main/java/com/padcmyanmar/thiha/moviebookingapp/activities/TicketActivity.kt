package com.padcmyanmar.thiha.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModel
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModelImpl
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DataCarrierVO
import com.padcmyanmar.thiha.moviebookingapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.activity_snack.*
import kotlinx.android.synthetic.main.activity_ticket.*

class TicketActivity : AppCompatActivity() {
    private var mCarrierData: DataCarrierVO? = null
    companion object{
        private const val EXTRA_CARRIER_DATA = "EXTRA_CARRIER_DATA"
        fun newIntent(context: Context, data: String): Intent {
            val intent = Intent(context, TicketActivity::class.java)
            intent.putExtra(EXTRA_CARRIER_DATA, data)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)
        mCarrierData = Gson().fromJson(intent.getStringExtra(EXTRA_CARRIER_DATA), DataCarrierVO::class.java)
        setUpListener()
        setUpTicketUI()
    }
    private fun setUpTicketUI() {
        //BarCode
        val barcodeEncoder = BarcodeEncoder()
        val bitmap =
            barcodeEncoder.encodeBitmap(mCarrierData?.bookingNo, BarcodeFormat.CODE_128, 140, 50)
        ivBarCode.setImageBitmap(bitmap)
        //UI Update With DataS
        mCarrierData?.let {
            tvBookNoDigit.text = it.bookingNo ?: ""
            tvTicketMovieTitle.text = it.name ?: ""
            tvShowTimeDate.text = " ${it.timeslotTime} - ${it.formatDate()}"
            tvThrater.text = it.cinemaName
            tvRowNumber.text = it.row
            tvPriceCount.text = "$ ${it.totalPrice}"
            tvSeatsNumber.text = it.seatNumber
            tvMovieTypeAndRunTime.text = it.runtime
            Glide.with(this)
                .load("$IMAGE_BASE_URL${it.posterPath}")
                .into(ivPostPathFromTicket)
        }

    }


    private fun setUpListener(){
        btnTicketClose.setOnClickListener{
           startActivity(MovieChoiceActivity.newIntent(this))
            mCarrierData = null
        }
    }


}