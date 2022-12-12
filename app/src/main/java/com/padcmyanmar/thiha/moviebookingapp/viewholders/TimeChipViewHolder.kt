package com.padcmyanmar.thiha.moviebookingapp.viewholders

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CinemaTimeslotsVO
import com.padcmyanmar.thiha.moviebookingapp.delegates.CinemaTimeslotDelegate
import kotlinx.android.synthetic.main.view_holder_time_chip.view.*

class TimeChipViewHolder(itemView: View ,private val mDelegate: CinemaTimeslotDelegate):RecyclerView.ViewHolder(itemView) {



    fun bindData(timeslot: CinemaTimeslotsVO) {
        itemView.tvMovieTime.text = timeslot.startTime
        itemView.setOnClickListener {
            timeslot.cinemaDayTimeslotId?.let {
                mDelegate.onTapTimeSlot(it)
            }
        }

        if (timeslot.isSelected == true){
            itemView.tvMovieTime.setTextColor(Color.WHITE)
//            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.primary_color))
            itemView.background = ContextCompat.getDrawable(
                itemView.context,
                R.drawable.background_time_chip_selected
            )
        }


    }

}