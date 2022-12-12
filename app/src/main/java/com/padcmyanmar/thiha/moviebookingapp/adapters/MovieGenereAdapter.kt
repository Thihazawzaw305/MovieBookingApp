package com.padcmyanmar.thiha.moviebookingapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.vos.GenreVO
import com.padcmyanmar.thiha.moviebookingapp.viewholders.MovieGenereViewHolder


class MovieGenereAdapter : RecyclerView.Adapter<MovieGenereViewHolder>() {
    private var mMovieGenreList : List<GenreVO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieGenereViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie_genre, parent, false)
        return MovieGenereViewHolder(view)

    }

    override fun onBindViewHolder(holder: MovieGenereViewHolder, position: Int) {
        if (mMovieGenreList.isNotEmpty()) {
            holder.bindData(mMovieGenreList[position])

        }

    }

    override fun getItemCount(): Int {
        return mMovieGenreList.count()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(movieGenreList: List<GenreVO>) {
        mMovieGenreList = movieGenreList
        notifyDataSetChanged()
    }
}