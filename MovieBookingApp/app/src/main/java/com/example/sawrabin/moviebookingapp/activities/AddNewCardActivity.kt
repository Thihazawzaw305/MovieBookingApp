package com.example.sawrabin.moviebookingapp.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModel
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import com.example.sawrabin.moviebookingapp.data.vos.CardVO
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_new_card.*

class AddNewCardActivity : AppCompatActivity() {
    private var mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl

    companion object {

        const val IE_DATA_TO_RETURN = "DATA TO RETURN"
        fun newIntent(context: Context): Intent {
            return Intent(context, AddNewCardActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_card)
        setUpOnClickListener()


    }

    private fun setUpOnClickListener() {
        ivBackAddNewCard.setOnClickListener {
            super.onBackPressed()
        }

        tvAddNewCardConfirmBtn.setOnClickListener {
            val intent = Intent()
            intent.putExtra(IE_DATA_TO_RETURN, "ADD CARD SUCCESSFULLY")
            createNewCard()


        }
    }

    private fun createNewCard() {
        mMovieBookingModel.createNewCard(
            cardNumber = etCardNo.text.toString(),
            cardHolder = etCardHolder.text.toString(),
            expDate = etExpDate.text.toString(),
            cvc = etCCV.text.toString(),
            onSuccess = {
                setResult(Activity.RESULT_OK, intent)
                finish()
            },
            onFailure = { Snackbar.make(window.decorView, it, Snackbar.LENGTH_LONG).show() }
        )
    }
}

