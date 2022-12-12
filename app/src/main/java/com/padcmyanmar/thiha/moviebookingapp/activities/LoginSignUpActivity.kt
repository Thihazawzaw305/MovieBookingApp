package com.padcmyanmar.thiha.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.adapters.LoginSignUpViewPagerAdapter
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModel
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModelImpl
import com.padcmyanmar.thiha.moviebookingapp.delegates.LoginSignUpButtonActionsDelegate

import kotlinx.android.synthetic.main.activity_login.*


class  LoginSignUpActivity : AppCompatActivity(), LoginSignUpButtonActionsDelegate {


    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl


    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, LoginSignUpActivity::class.java)

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpViewPager()
        setUpTabLayout()
        val viewPagerAdapter = LoginSignUpViewPagerAdapter(this)
        vpUserInput.adapter = viewPagerAdapter

    }

    // set up tab
    private fun setUpTabLayout() {
        TabLayoutMediator(tbTab, vpUserInput) { tab, position ->

            when (position) {
                0 -> {
                    tab.text = getString(R.string.lbl_login)
                }

                1 -> {
                    tab.text = getString(R.string.lbl_sign_in)
                }

                else -> {
                    tab.text = ""
                }
            }
        }.attach()
    }

    // set up View Pager
    private fun setUpViewPager() {
        val loginSignInViewPager = LoginSignUpViewPagerAdapter(this)
        vpUserInput.adapter = loginSignInViewPager
    }


    override fun onTapConfirmForSignUp(
        name: String,
        email: String,
        phone: String,
        password: String,
    ) {


        mMovieBookingModel.registerUser(
            name = name,
            email = email,
            phone = phone,
            password = password,


            onSuccess = {
                startActivity(MovieChoiceActivity.newIntent(this))

            },
            onFailure = {
                showError(it)
            }
        )


    }


    override fun onTapConfirmForLogin(email: String, password: String) {
        mMovieBookingModel.userLogin(
            email = email,
            password = password,

            onSuccess = {
                startActivity(MovieChoiceActivity.newIntent(this))
            },
            onFailure = {
                showError(it)
            }
        )


    }


    private fun showError(error: String) {
        Snackbar.make(window.decorView, error, Snackbar.LENGTH_LONG).show()
    }
}








