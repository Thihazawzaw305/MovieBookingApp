package com.padcmyanmar.thiha.moviebookingapp.viewholders

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.thiha.moviebookingapp.adapters.TimeChipAdapter
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CinemaDataVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CinemaTimeslotsVO
import com.padcmyanmar.thiha.moviebookingapp.delegates.CinemaTimeslotDelegate
import kotlinx.android.synthetic.main.view_holder_cinema.view.*


class CinemaViewHolder(itemView: View,private var mDelegate : CinemaTimeslotDelegate):RecyclerView.ViewHolder(itemView) {

    private var mTimeSlotList: List<CinemaTimeslotsVO> = listOf()


    fun bindData(cinema: CinemaDataVO) {
        this.mTimeSlotList = cinema.timeslots ?: listOf()
        itemView.tvCinemaName.text = cinema.cinema
        val mTimeChipAdapter = TimeChipAdapter(mDelegate)
        itemView.rvTimeChip.adapter = mTimeChipAdapter
        itemView.rvTimeChip.layoutManager = GridLayoutManager(itemView.context, 3)
       mTimeChipAdapter.setNewData(mTimeSlotList)
    }

}