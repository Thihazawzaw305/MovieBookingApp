package com.padcmyanmar.thiha.moviebookingapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.vos.MovieSeatVO
import com.padcmyanmar.thiha.moviebookingapp.delegates.MovieSeatDelegate
import com.padcmyanmar.thiha.moviebookingapp.viewholders.MovieSeatViewHolder

class MovieSeatAdapter(private var mMovieSeats: List<MovieSeatVO> = listOf(),private var mDelegate : MovieSeatDelegate):RecyclerView.Adapter<MovieSeatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSeatViewHolder {

     val view =   LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie_seat,parent,false)
        return MovieSeatViewHolder(view, mDelegate)
    }

    override fun onBindViewHolder(holder: MovieSeatViewHolder, position: Int) {
        if (mMovieSeats.isNotEmpty()){
            holder.bindData(mMovieSeats[position])
        }
    }

    override fun getItemCount(): Int {
    return mMovieSeats.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(movieSeats: List<MovieSeatVO>){
        this.mMovieSeats =movieSeats
        notifyDataSetChanged()
    }
}