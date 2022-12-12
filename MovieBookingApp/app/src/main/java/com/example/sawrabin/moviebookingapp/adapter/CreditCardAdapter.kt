package com.example.sawrabin.moviebookingapp.adapter

import alirezat775.lib.carouselview.CarouselAdapter
import alirezat775.lib.carouselview.CarouselView
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.vos.CardVO
import com.example.sawrabin.moviebookingapp.data.vos.DataVO

class CreditCardAdapter() :CarouselAdapter() {

private var mCardList: List<CardVO> = listOf()
    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
if (mCardList.isNotEmpty()){
}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_carousel,parent,false)
   return CreditCardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mCardList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(cardList:List<CardVO>){
        mCardList=cardList
        notifyDataSetChanged()
    }

    inner class CreditCardViewHolder(itemView: View): CarouselViewHolder(itemView){
        init {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context,R.color.white))
        }

        fun bindData(card:CardVO){

        }
    }
}