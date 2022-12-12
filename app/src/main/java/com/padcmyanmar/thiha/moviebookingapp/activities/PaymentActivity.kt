package com.padcmyanmar.thiha.moviebookingapp.activities


import alirezat775.lib.carouselview.Carousel
import alirezat775.lib.carouselview.CarouselListener
import alirezat775.lib.carouselview.CarouselView
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.adapters.CreditCardAdapter
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModel
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModelImpl
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CardVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DataCarrierVO
import kotlinx.android.synthetic.main.activity_payment.*

class PaymentActivity : AppCompatActivity() {

    private var mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl
    var mCarrierData: DataCarrierVO? = null
    var mCardList: List<CardVO> = listOf()
    var mSelectedCardNumber: Int = 0
    lateinit var mCreditCardAdapter: CreditCardAdapter

    companion object {

       private const val EXTRA_CARRIER_DATA = "EXTRA_CARRIER_DATA"
        fun newIntent(context: Context, data: String): Intent {
            val intent = Intent(context, PaymentActivity::class.java)
            intent.putExtra(EXTRA_CARRIER_DATA, data)
            return intent


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        setUpTotalPrice()
        setUpAddNewCard()
        setUpCarousel()
        setUpListener()
        setUpPaymentConfrim()
        requestData()
    }
    private fun setUpTotalPrice() {
        val mData = intent.getStringExtra(SnackActivity.EXTRA_CARRIER_DATA)
        mCarrierData = Gson().fromJson(mData, DataCarrierVO::class.java)
        mCarrierData?.let {
            tvAmount.text = "$ ${it.totalPrice}"
        }
    }


    private fun requestData() {
        mMovieBookingModel.getProfile(
            onSuccess = {
                it.cards?.let { cardlist ->
                    mCardList = cardlist.reversed()
                }
                mSelectedCardNumber = mCardList.firstOrNull()?.id ?: 0
                Log.println(Log.INFO, "selected", mSelectedCardNumber.toString())
                mCardList.firstOrNull()?.isSelected = true
                mCreditCardAdapter.setNewData(mCardList)
            }, onFailure = {
                Snackbar.make(window.decorView, it, Snackbar.LENGTH_LONG).show()
            }
        )
    }



    private fun setUpAddNewCard() {
        ivAdd.setOnClickListener {
       //     startActivityForResult(AddNewCardActivity.newIntent(this), 100)

        }

        tvAddNewCard.setOnClickListener {
            startActivityForResult(AddNewCardActivity.newIntent(this), 100)

        }

    }

    private fun setUpPaymentConfrim() {
        btnPaymentConfirm.setOnClickListener {
            if (mCardList.isNotEmpty()) {
                requestCheckOut()
            } else showError("Please Add A Card To Continue")
        }

    }


    private fun setUpCarousel() {
        mCreditCardAdapter = CreditCardAdapter()
        val carousel = Carousel(this, carouselRecyclerview, mCreditCardAdapter)
        carousel.setOrientation(CarouselView.HORIZONTAL, false)
        carousel.scaleView(true)
        mCreditCardAdapter.setNewData(mCardList)
        carousel.addCarouselListener(object : CarouselListener {
            override fun onPositionChange(position: Int) {
                mSelectedCardNumber = mCardList[position].id ?: 0
                mCardList[position].isSelected = true
                mCardList.forEach {
                    if (it.id != mSelectedCardNumber) {
                        it.isSelected = false
                    }
                }
                mCreditCardAdapter.setNewData(mCardList)
                Log.println(Log.INFO, "selected", mSelectedCardNumber.toString())
            }

            override fun onScroll(dx: Int, dy: Int) {
            }

        })
    }

    private fun setUpListener() {
        btnPaymentBack.setOnClickListener {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && data != null && resultCode == Activity.RESULT_OK) {
            requestData()
        }
    }

    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    private fun requestCheckOut() {
        mMovieBookingModel.checkOut(
//            row = mCarrierData?.row ?: "need row ",
//            seatNumber = mCarrierData?.seatNumber ?: "need seat number ",
//            snacks = mCarrierData?.snack ?: listOf(),
//            bookingDate = mCarrierData?.bookDate ?: "need booking date ",
//            movieId = mCarrierData?.movie_id ?: 0,
//            cinemaDayTimeslotId = mCarrierData?.timeslot ?: 0,
            cardId = mSelectedCardNumber,
//            cinemaId = mCarrierData?.cinemaId ?: 0,
            onSuccess = {

                MovieBookingModelImpl.storeBookingNo(it.bookingNo?:"")
                mCarrierData?.bookingNo = it.bookingNo
                val carrierJson = Gson().toJson(mCarrierData, DataCarrierVO::class.java)
                startActivity(TicketActivity.newIntent(this, carrierJson))
//                mCarrierData?.bookingNo = it.bookingNo
//                val carrierJson = Gson().toJson(mCarrierData, DataCarrierVO::class.java)
//                startActivity(TicketActivity.newIntent(this, carrierJson))
            }, onFailure = {
                showError("wtf")
            })
    }
}