package com.padcmyanmar.thiha.moviebookingapp.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.thiha.moviebookingapp.data.vos.CastVO
import com.padcmyanmar.thiha.moviebookingapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.view_holder_cast.view.*

class CastViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    fun bindData(actor: CastVO){

        Glide.with(itemView.context)
            .load("$IMAGE_BASE_URL${actor.profilePath}")
            .into(itemView.ivCastImage)
        itemView.tvCastName.text = actor.name.toString()




    }
}