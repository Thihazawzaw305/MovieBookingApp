package com.example.sawrabin.moviebookingapp.activities


import alirezat775.lib.carouselview.Carousel
import alirezat775.lib.carouselview.CarouselView
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.adapter.CreditCardAdapter
import com.example.sawrabin.moviebookingapp.adapter.CreditCardCarouselAdapter
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModel
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import com.example.sawrabin.moviebookingapp.data.vos.CarrierVO
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager
import kotlinx.android.synthetic.main.activity_payment_account.*


class PaymentAccountActivity : AppCompatActivity() {

    lateinit var mCreditCardAdapter: CreditCardAdapter
    lateinit var mCreditCardCarouselAdapter: CreditCardCarouselAdapter
    private var mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl
    var selectedItem = 0
    var mCarrierData: CarrierVO? = null

    companion object {
        val IE_DATA_TO_Return = "Data to Return"
        private const val EXTRA_CARRIER_DATA = "EXTRA_CARRIER_DATA"
        fun newIntent(context: Context, data: String): Intent {
            val intent = Intent(context, PaymentAccountActivity::class.java)
            intent.putExtra(EXTRA_CARRIER_DATA, data)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_account)
        setUpCarousel()

        val mData = intent.getStringExtra(SnackActivity.EXTRA_CARRIER_DATA)
        mCarrierData = Gson().fromJson(mData, CarrierVO::class.java)
        mCarrierData?.let {
            tvPaymentAmount.text = "$ ${it.totalPrice}"
        }
        setUpOnClickListener()
        requestData()


    }

    private fun requestData() {
        mMovieBookingModel.getProfile(
            onSuccess = {
                mCreditCardCarouselAdapter.setNewData(it.cards ?: listOf())
                Log.println(Log.INFO, "Cards", it.toString())
            }, onFailure = {
                Snackbar.make(window.decorView, it, Snackbar.LENGTH_LONG).show()
            }
        )
    }

    private fun setUpOnClickListener() {
        tvPaymentAccountConfirm.setOnClickListener {
            Snackbar.make(window.decorView, selectedItem.toString(), Snackbar.LENGTH_LONG).show()
        }

        tvAddNewCard.setOnClickListener {
            startActivityForResult(AddNewCardActivity.newIntent(this), 100)
        }

//        ivBtnBackPaymentAccount.setOnClickListener {
//            startActivity(SnackActivity.newIntent(this))
//        }
    }

    private fun setUpCarousel() {
//        mCreditCardAdapter = CreditCardAdapter()
//        val carousel = Carousel(this, rvCarousel, mCreditCardAdapter)
//        carousel.setOrientation(CarouselView.HORIZONTAL, false)
//        carousel.scaleView(true)
        mCreditCardCarouselAdapter = CreditCardCarouselAdapter()
        carouselRecyclerview.adapter = mCreditCardCarouselAdapter
        carouselRecyclerview.apply {
            set3DItem(false)
            setAlpha(false)
            setInfinite(false)
            setFlat(true)
        }
        val carouselLayoutManager = carouselRecyclerview.getCarouselLayoutManager()
        selectedItem = carouselRecyclerview.getSelectedPosition()
        carouselRecyclerview.setItemSelectListener(object : CarouselLayoutManager.OnSelected {
            override fun onItemSelected(position: Int) {
                Snackbar.make(window.decorView, position.toString(), Snackbar.LENGTH_LONG).show()
            }
        })

        Log.println(Log.INFO, "Cards", selectedItem.toString())


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && data != null && resultCode == Activity.RESULT_OK) {
            Snackbar.make(window.decorView, "Hello", Snackbar.LENGTH_LONG).show()
            mMovieBookingModel.getProfile(
                onSuccess = {

                    mCreditCardCarouselAdapter.setNewData(it.cards ?: listOf())
                    Log.println(Log.INFO, "Cards", it.toString())
                }, onFailure = {
                    Snackbar.make(window.decorView, it, Snackbar.LENGTH_LONG).show()
                }
            )


        }
    }

}