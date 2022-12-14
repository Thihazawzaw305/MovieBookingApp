package com.example.sawrabin.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.sawrabin.moviebookingapp.R
import com.example.sawrabin.moviebookingapp.adapter.CastAdapter
import com.example.sawrabin.moviebookingapp.adapter.MovieGenreAdapter
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModel
import com.example.sawrabin.moviebookingapp.data.models.MovieBookingModelImpl
import com.example.sawrabin.moviebookingapp.data.vos.CarrierVO
import com.example.sawrabin.moviebookingapp.data.vos.MovieVO
import com.example.sawrabin.moviebookingapp.utils.BASE_IMAGE_URL
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID"
        fun newIntent(context: Context, movieId: Int): Intent {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, movieId)
            return intent
        }
    }

    private lateinit var mCastAdapter: CastAdapter
    private lateinit var mMovieGenreAdapter: MovieGenreAdapter
    private val mMovieBookingModel: MovieBookingModel = MovieBookingModelImpl
    private var mMovieName: String = ""
    private var carrierString: String = ""
    private var mImdbId: String? = null
    private var movieId: Int? = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        movieId = intent?.getIntExtra(EXTRA_MOVIE_ID, 0)
        showError(movieId.toString())
        setContentView(R.layout.activity_movie_details)
        setUpOnClickListener()

        setUpMovieGenreRecyclerView()
        setUpCastRecyclerView()

        movieId?.let {
            requestData(it)
        }


    }

    private fun requestData(movieId: Int) {
        //Network Call For Movie Detail
        mMovieBookingModel.getMovieDetail(movieId = movieId.toString(), onSuccess = { movieDetail ->
            bindData(movieDetail)
            mMovieGenreAdapter.setNewData(movieDetail.genres ?: listOf())
            mImdbId = movieDetail.imdbId ?: ""
            //Network Call For IMDB Rating
            mImdbId?.let { imdbID ->
                getImdbRating(imdbID)
            }
            mMovieName = movieDetail.title.toString()
        }, onFailure = { showError(it) })

        //Network Call For Cast List
        mMovieBookingModel.getCredits(
            movieId = movieId.toString(),
            onSuccess = {
                mCastAdapter.setNewData(it)

            },
            onFailure = { showError(it) })


    }

    private fun getImdbRating(imdbID: String) {
        mMovieBookingModel.getImdbRating(
            imdbId = imdbID, onSuccess = { movieVO ->
                "IMDB ${movieVO.voteAverage}".also { tvImdbRating.text = it }
                rbMovieDetail.rating = movieVO.voteAverage?.div(2)?.toFloat() ?: 0.0F
            }, onFailure = { error ->
                showError(error)
            }
        )
    }

    private fun bindData(movie: MovieVO) {
        tvMovieNameDetail.text = movie.title ?: ""
        Glide.with(this)
            .load("$BASE_IMAGE_URL${movie.posterPath}")
            .into(ivMovieDetail)
        tvPlotSummary.text = movie.overview ?: ""
        movie.runtime?.let {
            tvMovieDetailDuration.text = "$it mins"
        }
        this.mMovieName = movie.title ?: ""
    }

    private fun setUpOnClickListener() {
        tvGetYourTicket.setOnClickListener {
            //Creating Data To Send Into Another Page
            val request = CarrierVO(movieId, mMovieName)
            carrierString = Gson().toJson(request, CarrierVO::class.java)
            startActivity(MovieTimeActivity.newIntent(this, this.carrierString))

        }

        ivBackMovieDetail.setOnClickListener {
            startActivity(HomeActivity.newIntent(this))
        }
    }

    private fun setUpMovieGenreRecyclerView() {
        mMovieGenreAdapter = MovieGenreAdapter()
        rvMovieGenre.adapter = mMovieGenreAdapter
        rvMovieGenre.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

    }

    private fun setUpCastRecyclerView() {
        mCastAdapter = CastAdapter()
        rvCast.adapter = mCastAdapter
        rvCast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }


    private fun showError(error: String) {
        Snackbar.make(window.decorView, error, Snackbar.LENGTH_LONG).show()
    }
}