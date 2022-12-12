package com.padcmyanmar.thiha.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.adapters.CastAdapter
import com.padcmyanmar.thiha.moviebookingapp.adapters.MovieGenereAdapter
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DataCarrierVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.GenreVO
import com.padcmyanmar.thiha.moviebookingapp.data.vos.MovieVO
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModel
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModelImpl
import com.padcmyanmar.thiha.moviebookingapp.utils.IMAGE_BASE_URL
import kotlinx.android.synthetic.main.activity_movie_details.*


class MovieDetailsActivity : AppCompatActivity() {
    lateinit var mCastAdapter: CastAdapter
    lateinit var mMovieGenereAdapter: MovieGenereAdapter
    private var movieId: Int? = 0
    private var mMovieName: String = ""
    private var dataCarrierString: String = ""
    private var mPosterPath: String = ""
    private var mMovieDuration: String = ""
    companion object{
        private const val EXTRA_MOVIE_ID ="EXTRA_MOVIE_ID"
        fun newIntent(context: Context, movieId : Int) : Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID,movieId)
            return intent
        }
    }


//model
    private val mMovieBookingModel : MovieBookingModel = MovieBookingModelImpl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        movieId =  MovieBookingModelImpl.getBookingData()?.movie_id
     //   movieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)
        setUpListener()
        setUpGetYourTicket()
        setUpCMovieGenereRecyclerView()
        setUpCastRecyclerView()
        movieId?.let {
           // Snackbar.make(window.decorView,movieId.toString()?:"no",Snackbar.LENGTH_LONG).show()
            requestData(it)
        }


    }


    private fun setUpListener(){
        btnBack.setOnClickListener{
            super.onBackPressed()
        }
    }

    private fun setUpGetYourTicket() {
        btnGetYourTicket.setOnClickListener {
            MovieBookingModelImpl.storeMovieDetailData(
               runtime = mMovieDuration, name = mMovieName, posterPath = mPosterPath
            )
            val request = DataCarrierVO(movie_id = movieId, name = mMovieName, posterPath = mPosterPath, runtime = mMovieDuration)
           dataCarrierString = Gson().toJson(request, DataCarrierVO::class.java)
            startActivity(GetTicketActivity.newIntent(this, this.dataCarrierString))
//            dataCarrierString =  Gson().toJson(DataCarrierVO( movieId,mMovieName), DataCarrierVO::class.java)
//            startActivity(GetTicketActivity.newIntent(this,this.dataCarrierString))

        }}

    private fun  setUpCMovieGenereRecyclerView(){

        mMovieGenereAdapter = MovieGenereAdapter()
        rvMovieGenereList.adapter = mMovieGenereAdapter
        rvMovieGenereList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
    }

    private fun  setUpCastRecyclerView(){

        mCastAdapter = CastAdapter()
        rvCastlist.adapter = mCastAdapter
        rvCastlist.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
    }

    private fun requestData(movieId: Int){
        mMovieBookingModel.getMovieDetails(
            movieId = movieId.toString(),
            onSuccess = {
                bindData(it)
                mMovieGenereAdapter.setNewData(it.genres ?: listOf())

            //    it.genres?.let { it1 -> mMovieGenereAdapter.setNewData(it1) }
            },
            onFailure = {
                // show error
                showError(it)
            }
        )

        mMovieBookingModel.getCast(
            movieId = movieId.toString(),
            onSuccess = {

           mCastAdapter.setNewData(it)
            },
            onFailure = {
                //show error
                showError(it)
            }
        )


    }

    private fun bindData(movie: MovieVO){
        Glide.with(this)
            .load("$IMAGE_BASE_URL${movie.posterPath}")
            .into(ivMovieDetails)

        tvMovieTitle.text = movie.title?:"need data"
        collapsingToolBar.title = movie.title?:"need data"
        tvIMDB.text = "IMDb${movie.voteAverage.toString()}"
        tvTime.text = movie.calculateRunTime()
        rating.rating = movie.getRatingBaseOnFiveStars()
        tvPlots.text = movie.overview?:"need data"
        mPosterPath = movie.posterPath ?: ""
        mMovieDuration=movie.calculateRunTime()
        this.mMovieName =movie.title?:"need data"
      // bindGenres(movie,movie.genres ?: listOf())


}


    private fun bindGenres(
        movie: MovieVO,
        genres: List<GenreVO>
    ){


        movie.genres?.count()?.let{
        mMovieGenereAdapter.setNewData(genres)


        }


    }

    private fun showError(error: String) {
        Snackbar.make(window.decorView, error, Snackbar.LENGTH_LONG).show()
    }


}