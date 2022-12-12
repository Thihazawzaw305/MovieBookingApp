package com.padcmyanmar.thiha.moviebookingapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CinemaTimeslotsVO
import com.padcmyanmar.thiha.moviebookingapp.delegates.CinemaTimeslotDelegate
import com.padcmyanmar.thiha.moviebookingapp.viewholders.TimeChipViewHolder

class TimeChipAdapter(val mDelegate : CinemaTimeslotDelegate) : RecyclerView.Adapter<TimeChipViewHolder>() {
    private var mTimeSlot: List<CinemaTimeslotsVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeChipViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_time_chip, parent, false)

        return TimeChipViewHolder(view,mDelegate)
    }

    override fun onBindViewHolder(holder: TimeChipViewHolder, position: Int) {
        if (mTimeSlot.isNotEmpty()) {
            holder.bindData(mTimeSlot[position])
        }
    }

    override fun getItemCount(): Int {
        return mTimeSlot.count()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(timeslotList: List<CinemaTimeslotsVO>) {
        mTimeSlot = timeslotList
        notifyDataSetChanged()
    }
}

