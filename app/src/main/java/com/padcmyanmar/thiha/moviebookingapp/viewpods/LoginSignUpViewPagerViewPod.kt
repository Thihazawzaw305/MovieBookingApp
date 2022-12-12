package com.padcmyanmar.thiha.moviebookingapp.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.padcmyanmar.thiha.moviebookingapp.delegates.LoginSignUpButtonActionsDelegate
import com.padcmyanmar.thiha.moviebookingapp.delegates.LoginSignUpViewPodDelegate
import kotlinx.android.synthetic.main.view_pod_confrim.view.*


class LoginSignUpViewPagerViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    lateinit var mDelegate: LoginSignUpViewPodDelegate

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

//     fun setDelegate(delegate: LoginSignUpButtonsActionDelegate)
//     {
//        this.mDelegate = delegate
//
//
//    }
//
//    fun setUpViewPodClickListener(context: Context){
//
//        btnConfirm.setOnClickListener {
//            mDelegate.onTapConfrim()
//        }
//
//        btnSignInGoogle.setOnClickListener {
//            mDelegate.onTapConfrim()
//        }
//
//        btnSignInWithFacebook.setOnClickListener {
//            mDelegate.onTapConfrim()
//        }
//
//
//    }


    fun setUpViewPod(delegate: LoginSignUpViewPodDelegate) {
        mDelegate = delegate
        btnConfirm.setOnClickListener {
            mDelegate.onTapConfirm()

        }

    }


}