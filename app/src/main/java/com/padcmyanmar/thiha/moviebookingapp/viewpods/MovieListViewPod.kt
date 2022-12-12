package com.padcmyanmar.thiha.moviebookingapp.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.thiha.moviebookingapp.adapters.MovieAdapter
import com.padcmyanmar.thiha.moviebookingapp.data.vos.MovieVO
import com.padcmyanmar.thiha.moviebookingapp.delegates.MovieViewHolderDelegate
import kotlinx.android.synthetic.main.view_pod_movie_list.view.*

class MovieListViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    lateinit var mMovieAdapter: MovieAdapter
    lateinit var mDelegate : MovieViewHolderDelegate

    override fun onFinishInflate() {
       // setUpMovieRecyclerView()
        super.onFinishInflate()
    }

    fun setUpMovieListViewPod(delegate: MovieViewHolderDelegate){
        setDelegate(delegate)
        setUpMovieRecyclerView()
    }

    fun setData(movieList: List<MovieVO>){
        mMovieAdapter.setNewData(movieList)
    }


    fun setUpMovieTitle(titleText : String){
        tvMovieCategory.text = titleText

    }

    private fun setDelegate(delegate: MovieViewHolderDelegate){
        this.mDelegate = delegate


    }

    private fun  setUpMovieRecyclerView(){
        mMovieAdapter = MovieAdapter(mDelegate)
        rvMovielist.adapter = mMovieAdapter
        rvMovielist.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
 //       rvMovielist.layoutManager = GridLayoutManager(context,2)
    }
}