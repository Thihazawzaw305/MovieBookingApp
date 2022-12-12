package com.padcmyanmar.thiha.moviebookingapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.delegates.LoginSignUpButtonActionsDelegate
import com.padcmyanmar.thiha.moviebookingapp.delegates.LoginSignUpViewPodDelegate
import com.padcmyanmar.thiha.moviebookingapp.viewpods.LoginSignUpViewPagerViewPod
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment(), LoginSignUpViewPodDelegate {

    lateinit var mDelegate: LoginSignUpButtonActionsDelegate
    lateinit var email: AppCompatEditText
    lateinit var password: AppCompatEditText
    lateinit var mLoginSignUpViewPagerViewPod: LoginSignUpViewPagerViewPod

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mDelegate = context as LoginSignUpButtonActionsDelegate

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLoginSignUpViewPagerViewPod = vpLogin as LoginSignUpViewPagerViewPod
        email = view.etEnterEmailForLogin
        password = view.etEnterPasswordForLogin
        mLoginSignUpViewPagerViewPod.setUpViewPod(this)

    }


    override fun onTapConfirm() {

        mDelegate.onTapConfirmForLogin(
            email = email.text.toString(),
            password = password.text.toString()
        )
    }
}











