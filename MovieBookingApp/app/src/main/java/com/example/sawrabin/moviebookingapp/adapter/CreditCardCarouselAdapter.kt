package com.example.sawrabin.moviebookingapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.data.vos.CardVO
import com.example.sawrabin.moviebookingapp.viewholder.CreditCardCarouselViewHolder

class CreditCardCarouselAdapter : RecyclerView.Adapter<CreditCardCarouselViewHolder>() {
    private var mCreditCardList: List<CardVO> = listOf()
    override fun onBindViewHolder(holder: CreditCardCarouselViewHolder, position: Int) {
        if (mCreditCardList.isNotEmpty()) {
            holder.bindData(mCreditCardList[position])
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CreditCardCarouselViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_carousel, parent, false)
        return CreditCardCarouselViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mCreditCardList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(cardList: List<CardVO>) {
        mCreditCardList = cardList
        notifyDataSetChanged()
    }
}