package com.padcmyanmar.thiha.moviebookingapp.viewholders

import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DateVO
import com.padcmyanmar.thiha.moviebookingapp.delegates.CinemaDateDelegate
import kotlinx.android.synthetic.main.view_holder_date.view.*

class DateViewHolder(itemView : View, private var mDelegate : CinemaDateDelegate):RecyclerView.ViewHolder(itemView) {
    private var mDate: DateVO? = null

    init {
        itemView.setOnClickListener {
            mDate?.let {
                mDelegate.onTapCinemaDate(it.dayFull ?: "")

            }


        }

    }


    fun bindData(date: DateVO) {

        Log.println(Log.INFO, "TapDate", date.isChosed.toString())
        mDate = date
        itemView.tvDays.text = date.cinemaDate
        itemView.tvDate.text = date.cinemaDay
        if (date.isChosed == true) {
            itemView.tvDays.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.white
                )
            )
            itemView.tvDate.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.white
                )
            )
        } else {
            itemView.tvDays.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.play_button_background_color
                )
            )
            itemView.tvDate.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.play_button_background_color
            )
            )
        }
    }


}