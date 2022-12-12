package com.padcmyanmar.thiha.moviebookingapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CastVO
import com.padcmyanmar.thiha.moviebookingapp.viewholders.CastViewHolder


class CastAdapter:RecyclerView.Adapter<CastViewHolder>() {

    private var mCast: List<CastVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_cast,parent,false)
        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {


        if (mCast.isNotEmpty()) {
            holder.bindData(mCast[position])
        }
    }

    override fun getItemCount(): Int {
        return mCast.count()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(Cast: List<CastVO>) {
        mCast = Cast
        notifyDataSetChanged()
    }


}