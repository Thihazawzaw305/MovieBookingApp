package com.padcmyanmar.thiha.moviebookingapp.viewholders

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.vos.PaymentVO
import com.padcmyanmar.thiha.moviebookingapp.delegates.PaymentDelegate
import kotlinx.android.synthetic.main.view_holder_card_payment.view.*

class CardPaymentViewHolder(itemView : View, private var mDelegate : PaymentDelegate):RecyclerView.ViewHolder(itemView) {
    private var mPayment: PaymentVO? = null

    fun bindData(payment: PaymentVO) {
        mPayment = payment
        itemView.tvCreditCardTitle.text = payment.name ?: ""
        itemView.tvPaymentCardNames.text = payment.description ?: ""
        itemView.setOnClickListener {
            mDelegate.onTapItem(payment.id ?: 0)
        }
        if (payment.isSelected == true) {
            setUpUI(R.color.primary_color)
        } else
            setUpUI(R.color.secondary_text_color)
    }

    private fun setUpUI(Color: Int) {
        itemView.ivCard.setColorFilter(ContextCompat.getColor(itemView.context, Color))
        itemView.tvCreditCardTitle.setTextColor(ContextCompat.getColor(itemView.context, Color))
        itemView.tvPaymentCardNames.setTextColor(ContextCompat.getColor(itemView.context, Color))
    }
}