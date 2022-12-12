package com.padcmyanmar.thiha.moviebookingapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.vos.PaymentVO
import com.padcmyanmar.thiha.moviebookingapp.delegates.PaymentDelegate
import com.padcmyanmar.thiha.moviebookingapp.viewholders.CardPaymentViewHolder

class CardAdapter (private var mDelegate : PaymentDelegate): RecyclerView.Adapter<CardPaymentViewHolder>() {

  private var mPaymentList: List<PaymentVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardPaymentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_card_payment, parent, false)
        return CardPaymentViewHolder(view,mDelegate)
    }

    override fun onBindViewHolder(holderPayment: CardPaymentViewHolder, position: Int) {
        if (mPaymentList.isNotEmpty()) {
            holderPayment.bindData(mPaymentList[position])
    }




}
    override fun getItemCount(): Int {
        return mPaymentList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(payment: List<PaymentVO>) {
        mPaymentList = payment
        notifyDataSetChanged()
    }
}