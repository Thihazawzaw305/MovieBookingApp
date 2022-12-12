package com.padcmyanmar.thiha.moviebookingapp.viewholders

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.thiha.moviebookingapp.data.vos.SnackVO

import com.padcmyanmar.thiha.moviebookingapp.delegates.SnackDelegate
import kotlinx.android.synthetic.main.view_holder_snack_set.view.*

class SnackViewHolder(itemView : View, private var mDelegate : SnackDelegate):RecyclerView.ViewHolder(itemView) {

    private var mSnack: SnackVO? = null

    fun bindData(snack: SnackVO) {
        mSnack = snack
        itemView.tvSnackSet.text = snack.name ?: ""
        itemView.tvSncakItems.text = snack.description
        itemView.tvDollar.text = "${snack.price} $"
        Log.println(Log.INFO,"quantity",snack.toString())
        itemView.btnMinus.setOnClickListener {
            mDelegate.onTapMinus(snack.id?:0)
        }

        itemView.btnPlus.setOnClickListener {
            mDelegate.onTapPlus(snack.id?:0)
        }

        itemView.tvAmount.text=snack.quantity.toString()
    }
}