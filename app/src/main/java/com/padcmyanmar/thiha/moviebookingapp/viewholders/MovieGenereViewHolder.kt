package com.padcmyanmar.thiha.moviebookingapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.thiha.moviebookingapp.data.vos.GenreVO
import kotlinx.android.synthetic.main.view_holder_movie_genre.view.*

class MovieGenereViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    fun bindData(MovieGenre: GenreVO){

       itemView.tvGenre.text = MovieGenre.name?:"need data"




    }

}