package com.example.sawrabin.moviebookingapp.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.data.vos.CardVO
import kotlinx.android.synthetic.main.view_holder_carousel.view.*

class CreditCardCarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){



    fun bindData(data:CardVO){
        itemView.tvCreditCardHolder.text= data.cardHolder
        itemView.tvCardCreditCard.text=data.cardNumber
        itemView.tvCreditCardHolderExp.text=data.expirationDate
    }
}