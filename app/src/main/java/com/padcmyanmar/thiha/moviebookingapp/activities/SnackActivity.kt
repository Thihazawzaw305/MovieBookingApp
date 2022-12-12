package com.padcmyanmar.thiha.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.adapters.CardAdapter
import com.padcmyanmar.thiha.moviebookingapp.adapters.SnackAdapter
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DataCarrierVO
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModel
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModelImpl
import com.padcmyanmar.thiha.moviebookingapp.data.vos.PaymentVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.SnackVO
import com.padcmyanmar.thiha.moviebookingapp.delegates.PaymentDelegate
import com.padcmyanmar.thiha.moviebookingapp.delegates.SnackDelegate
import kotlinx.android.synthetic.main.activity_snack.*

class SnackActivity : AppCompatActivity(), SnackDelegate, PaymentDelegate {

    lateinit var mSnackAdapter : SnackAdapter
    lateinit var mCardPaymentAdapter: CardAdapter
    private var mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl
    private var mSnackList: List<SnackVO> = listOf()
    private var mPaymentList: List<PaymentVO> = listOf()
    var mSubTotal: Int = 0
    var mCarrierData: DataCarrierVO? = null
    var mCarrierJson: String = ""
    companion object{

        const val EXTRA_CARRIER_DATA = "EXTRA_CARRIER_DATA"
        fun newIntent(context: Context, data: String) : Intent {
            val intent = Intent(context, SnackActivity::class.java)
            intent.putExtra(EXTRA_CARRIER_DATA, data)
            return intent

        }}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snack)
        val mData = intent.getStringExtra(EXTRA_CARRIER_DATA)
        mCarrierData = Gson().fromJson(mData, DataCarrierVO::class.java)
        mCarrierData?.let {
            mSubTotal = it.totalPrice?:0
            subTotalUpdate(it.totalPrice ?: 0)

        }

        setUpListener()
        setUpSnackRecyclerView()
        setUpCardRecyclerView()
        setUpPay()
        requestData()
    }
    private fun requestData() {
        mMovieBookingModel.getSnackList(
            onSuccess = {
                mSnackAdapter.setNewData(it)
                mSnackList = it

            }, onFailure = {
                Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
            }
        )

        mMovieBookingModel.getPaymentMethods(
            onSuccess = {
                mCardPaymentAdapter.setNewData(it)
                mPaymentList = it

            }, onFailure = {
                Snackbar.make(window.decorView, it, Snackbar.LENGTH_SHORT).show()
            }
        )
    }

    private fun  setUpSnackRecyclerView(){

        mSnackAdapter = SnackAdapter(this)
        rvSnackSetList.adapter = mSnackAdapter
        rvSnackSetList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

    }

    private fun setUpCardRecyclerView(){
        mCardPaymentAdapter = CardAdapter(this)
        rvCardList.adapter = mCardPaymentAdapter
        rvCardList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    private fun setUpListener(){
        btnSnackBack.setOnClickListener{
            super.onBackPressed()
        }
    }


    private fun setUpPay() {
        btnPay.setOnClickListener {
           val mSelectedSnackList = mSnackList.filter { it.quantity != 0 }
            MovieBookingModelImpl.storeSnackData( mSelectedSnackList,mSubTotal)
            mCarrierData?.let {
                it.snack=mSnackList
                it.totalPrice=mSubTotal
            }
            mCarrierJson = Gson().toJson(mCarrierData, DataCarrierVO::class.java)
            Log.println(Log.INFO, "Filter Snack", mCarrierJson.toString())
            startActivity(PaymentActivity.newIntent(this, mCarrierJson))
            }



        }



    private fun subTotalUpdate(subTotal: Int) {
        "Sub total: $subTotal $".also { tvSubTotal.text = it }
        "Pay  $subTotal $ ".also { tvPaySnackPrice.text = it }
    }

    override fun onTapItem(id: Int) {
        for (payment in mPaymentList) {
            payment.isSelected = payment.id == id
        }
        mCardPaymentAdapter.setNewData(mPaymentList)
    }

    override fun onTapPlus(id: Int) {
        for (snack in mSnackList) {
            if (snack.id == id) {
                snackOnPlusUpdate(snack)
            }
        }
    }

    override fun onTapMinus(id: Int) {
        for (snack in mSnackList) {
            if (snack.id == id) {
                if (snack.quantity != 0) {
                    snackOnMinusUpdate(snack)
                }
            }
        }
        mSnackAdapter.setNewData(mSnackList)
    }

    private fun snackOnPlusUpdate(snack: SnackVO) {
        snack.quantity++
        mSubTotal += snack.price ?: mSubTotal
        mSnackAdapter.setNewData(mSnackList)
        subTotalUpdate(mSubTotal)
    }

    private fun snackOnMinusUpdate(snack: SnackVO) {
        snack.quantity--
//        mSubTotal -= snack.price ?: 0
        subTotalUpdate(mSubTotal)
    }
    private fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}