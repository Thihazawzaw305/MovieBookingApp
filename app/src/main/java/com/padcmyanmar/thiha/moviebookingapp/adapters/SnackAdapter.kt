package com.padcmyanmar.thiha.moviebookingapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.vos.SnackVO

import com.padcmyanmar.thiha.moviebookingapp.delegates.SnackDelegate
import com.padcmyanmar.thiha.moviebookingapp.viewholders.SnackViewHolder

class SnackAdapter(private var mDelegate : SnackDelegate) : RecyclerView.Adapter<SnackViewHolder>() {
    private var mSnackList: List<SnackVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_snack_set, parent, false)
        return SnackViewHolder(view, mDelegate)
    }

    override fun onBindViewHolder(holder: SnackViewHolder, position: Int) {
        if (mSnackList.isNotEmpty()) {
            holder.bindData(mSnackList[position])
        }
    }

    override fun getItemCount(): Int {
        return mSnackList.count()
    }



    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(snackList: List<SnackVO>) {
        mSnackList = snackList
        notifyDataSetChanged()
    }
}