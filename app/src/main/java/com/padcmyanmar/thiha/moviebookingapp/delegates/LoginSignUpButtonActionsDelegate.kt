package com.padcmyanmar.thiha.moviebookingapp.delegates
interface LoginSignUpButtonActionsDelegate {

    fun onTapConfirmForSignUp( name : String,
                      email :String,
                      phone : String,
                      password : String,
    )

    fun onTapConfirmForLogin( email : String,
                              password : String,

    )
}