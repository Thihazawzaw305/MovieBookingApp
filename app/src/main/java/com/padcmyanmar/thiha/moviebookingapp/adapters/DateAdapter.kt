package com.padcmyanmar.thiha.moviebookingapp.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DateVO
import com.padcmyanmar.thiha.moviebookingapp.delegates.CinemaDateDelegate
import com.padcmyanmar.thiha.moviebookingapp.viewholders.DateViewHolder

class DateAdapter(private var mDelegate : CinemaDateDelegate):RecyclerView.Adapter<DateViewHolder>() {
    var selected_item: Int = 0
    private var mDateList: List<DateVO> = listOf()
    private var selectedItemPosition: Int = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_date,parent,false)
        return DateViewHolder(view, mDelegate)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        selectedItemPosition = holder.bindingAdapterPosition
        if (mDateList.isNotEmpty()) {
            holder.bindData(mDateList[position])
    }


}



    override fun getItemCount(): Int {
        return mDateList.count()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(dateList: List<DateVO>) {
        mDateList = dateList
        Log.println(Log.INFO, "TapSetNewData", mDateList.toString())
        notifyDataSetChanged()
    }

}