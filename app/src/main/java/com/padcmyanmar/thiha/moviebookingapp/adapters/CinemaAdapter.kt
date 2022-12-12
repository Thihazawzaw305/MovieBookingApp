package com.padcmyanmar.thiha.moviebookingapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CinemaDataVO
import com.padcmyanmar.thiha.moviebookingapp.delegates.CinemaTimeslotDelegate
import com.padcmyanmar.thiha.moviebookingapp.viewholders.CinemaViewHolder

class CinemaAdapter(private val mCinemaTimeslotDelegate: CinemaTimeslotDelegate):RecyclerView.Adapter<CinemaViewHolder> (){

    private var mCinemaDayList: List<CinemaDataVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_cinema,parent,false)
        return CinemaViewHolder(view,mCinemaTimeslotDelegate)
    }

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        if (mCinemaDayList.isNotEmpty()) {
            holder.bindData(mCinemaDayList[position])
        }
    }

    override fun getItemCount(): Int {
        return mCinemaDayList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(cinemaList: List<CinemaDataVO>) {
        mCinemaDayList = cinemaList
        notifyDataSetChanged()
    }
}