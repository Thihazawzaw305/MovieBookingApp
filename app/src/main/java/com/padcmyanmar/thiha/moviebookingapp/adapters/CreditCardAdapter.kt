package com.padcmyanmar.thiha.moviebookingapp.adapters


import alirezat775.lib.carouselview.CarouselAdapter
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CardVO
import kotlinx.android.synthetic.main.view_holder_credit_card.view.*

class CreditCardAdapter() : CarouselAdapter() {
    private var mCarouselViewHolder:CarouselAdapter.CarouselViewHolder?=null

    private var mCardList: List<CardVO> = listOf()
    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        if (mCardList.isNotEmpty()){

            mCarouselViewHolder=holder
            (mCarouselViewHolder as CreditCardViewHolder).bindData(mCardList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_credit_card,parent,false)
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

    inner class CreditCardViewHolder(itemView: View): CarouselViewHolder(itemView) {


        fun bindData(data: CardVO) {
            itemView.tvCreditCardHolderName.text = data.cardHolder
            val lastFourNumbers = data.cardNumber?.toLong()?.rem(10000).toString()
            itemView.tvCreditCardLastFourNumbers.text = lastFourNumbers
            itemView.tvCreditCardExpiresDate.text = data.expirationDate
            itemView.tvCardType.text = data.cardType
            if (data.isSelected == true) {
                itemView.background =
                    ContextCompat.getDrawable(itemView.context, R.drawable.background_credit_card)


            } else {

                itemView.background = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.backgroound_credit_card_unselected
                )
            }

        }

    }}




