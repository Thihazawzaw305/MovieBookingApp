package com.padcmyanmar.thiha.moviebookingapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModel
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModelImpl
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {
    val mMovieBookingModel : MovieBookingModel = MovieBookingModelImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        setUpButtom()
    }

    private fun setUpButtom() {
        btnGetStarted.setOnClickListener {
            if(MovieBookingModelImpl.checkLogin()) {
                startActivity(MovieChoiceActivity.newIntent(this))
            }
          else  startActivity(LoginSignUpActivity.newIntent(this))

        }
    }
}