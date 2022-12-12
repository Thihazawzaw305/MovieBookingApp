package com.padcmyanmar.thiha.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.snackbar.Snackbar
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModel
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModelImpl
import kotlinx.android.synthetic.main.activity_add_new_card.*
import kotlinx.android.synthetic.main.activity_snack.*

class AddNewCardActivity : AppCompatActivity() {
    private var mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl
    lateinit var cardNo: AppCompatEditText
    lateinit var cardHolderName: AppCompatEditText
    lateinit var cardExpDate: AppCompatEditText
    lateinit var cardCCV: AppCompatEditText

    companion object {
        const val IE_DATA_TO_RETURN = "DATA TO RETURN"
        fun newIntent(context: Context): Intent {
            return Intent(context, AddNewCardActivity::class.java)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_card)
        setUpCreditCard()
        setUpListener()
    }

    private fun setUpCreditCard() {
        cardNo = etCardNumber
        cardHolderName = etCardHolder
        cardExpDate = etExpirationDate
        cardCCV = etCvc
    }


    private fun setUpListener(){
        btnAddNewCardClose.setOnClickListener{
            super.onBackPressed()
        }

        btnAddnewCardConfirm.setOnClickListener{
            val intent = Intent()
            intent.putExtra(IE_DATA_TO_RETURN, "ADD CARD SUCCESSFULLY")
            createNewCard()

        }
    }

    private fun createNewCard() {
        when {
            (cardNo.length() != 16) -> {
                cardNo.error = "Please Type Valid Card No"
            }
            (cardHolderName.length() == 0) -> {
                cardHolderName.error = "Name Cannot Be Empty"
            }
            (cardExpDate.length() == 0) -> {
                cardExpDate.error = "Exp Date Cannot Be Empty"
            }
            (cardCCV.length() != 3) -> {
                cardCCV.error = "Invalid"
            }
            else -> requestCreateCard()
        }

    }
    private fun requestCreateCard() {
        mMovieBookingModel.createNewCard(
            cardNumber = cardNo.text.toString(),
            cardHolder = cardHolderName.text.toString(),
            expDate = cardExpDate.text.toString(),
            cvc = cardCCV.text.toString(),
            onSuccess = {
                setResult(RESULT_OK, intent)
                finish()
            },
            onFailure = { Snackbar.make(window.decorView, it, Snackbar.LENGTH_LONG).show() }
        )
    }
}