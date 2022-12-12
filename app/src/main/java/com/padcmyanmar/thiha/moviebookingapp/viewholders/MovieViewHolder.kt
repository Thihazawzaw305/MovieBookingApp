package com.padcmyanmar.thiha.moviebookingapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.thiha.moviebookingapp.data.vos.MovieVO
import com.padcmyanmar.thiha.moviebookingapp.delegates.MovieViewHolderDelegate
import com.padcmyanmar.thiha.moviebookingapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_movie.view.*

class MovieViewHolder(itemView: View,private val mDelegate: MovieViewHolderDelegate) : RecyclerView.ViewHolder(itemView) {
    private var mMovieVO : MovieVO? = null

init {
    itemView.setOnClickListener{
        mMovieVO?.let {
            mDelegate.onTapMovie(it.id)
        }

    }
}
    fun bindData(movie: MovieVO) {

        mMovieVO = movie

        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(itemView.ivMovieImage)

        itemView.tvMovieName.text = movie.title


    }



}