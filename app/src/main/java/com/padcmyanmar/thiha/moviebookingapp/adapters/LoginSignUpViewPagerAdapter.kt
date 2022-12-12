package com.padcmyanmar.thiha.moviebookingapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.padcmyanmar.thiha.moviebookingapp.fragments.LoginFragment
import com.padcmyanmar.thiha.moviebookingapp.fragments.SignInFragment


class LoginSignUpViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
      return  when(position) {

            0 -> LoginFragment()
          else ->SignInFragment()
        }
    }

}