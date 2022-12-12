package com.padcmyanmar.thiha.moviebookingapp.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.snackbar.Snackbar
import com.padcmyanmar.thiha.moviebookingapp.R
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModel
import com.padcmyanmar.thiha.moviebookingapp.data.models.MovieBookingModelImpl
import com.padcmyanmar.thiha.moviebookingapp.delegates.MovieViewHolderDelegate
import com.padcmyanmar.thiha.moviebookingapp.viewpods.MovieListViewPod
import kotlinx.android.synthetic.main.activity_movie_choice.*

class MovieChoiceActivity : AppCompatActivity(),MovieViewHolderDelegate {


    private val mMovieBookingModel : MovieBookingModel = MovieBookingModelImpl



   private var actionBarDrawerToggle : ActionBarDrawerToggle? = null
    companion object{

        fun newIntent(context: Context) : Intent {
            return Intent(context, MovieChoiceActivity::class.java)

        }
    }


   private lateinit var mNowShowingMovieListViewPod : MovieListViewPod
 private   lateinit var mComingSoonMovieListViewPod: MovieListViewPod


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_choice)
        //setUpToolbar()
        setUpViewPod()
        setUpDrawer()
        requestData()
        tapLogout()

    }

  private  fun requestData(){

// get profile
        mMovieBookingModel.getProfile(

            onSuccess = {
                it.name?.let {  name ->
                  tvUserName.text = name
                    tvUserNameInDrawer.text = name
                }
                tvDrawerUserEmail.text = it.email ?: ""

            }, onFailure = {
                Snackbar.make(window.decorView,it, Snackbar.LENGTH_LONG).show()

            }
        )
// now showing
        mMovieBookingModel.getNowShowingMovies(
            onSuccess = {
               mNowShowingMovieListViewPod.setData(it)
            },
            onFailure = {
                /// Show error Message
                showError(it)
            }
        )
// coming soon
        mMovieBookingModel.getComingSoonMovies(
            onSuccess = {
                mComingSoonMovieListViewPod.setData(it)
            },
            onFailure = {
                /// Show error Message
                showError(it)
            }
        )




    }

    private fun setUpViewPod(){
        mNowShowingMovieListViewPod = vpNowShowingMovie as MovieListViewPod
        mNowShowingMovieListViewPod.setUpMovieListViewPod(this)
        mComingSoonMovieListViewPod = vpComingSoonMovie as MovieListViewPod
        mComingSoonMovieListViewPod.setUpMovieListViewPod(this)
        mComingSoonMovieListViewPod.setUpMovieTitle("Coming Soon")
    }



    // App Bar leading Icon
    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menus)


    }

    override fun onTapMovie(movieId : Int) {
        MovieBookingModelImpl.insertMovieId(movieId)
        startActivity(MovieDetailsActivity.newIntent(this, movieId = movieId))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_discover,menu)
        return true
    }

    private fun setUpDrawer(){

        setSupportActionBar(toolbar)

       //toolbar.setLogo(R.drawable.menus)

        actionBarDrawerToggle = ActionBarDrawerToggle(this,DrawerLayout,R.string.drawer_open,R.string.drawer_close)
        actionBarDrawerToggle?.let {
            DrawerLayout.addDrawerListener(it)
            it.syncState()
        }
        toolbar.elevation = 0.0f
        supportActionBar?.elevation = 0.0f
        supportActionBar?. title = ""
      //  supportActionBar?.setIcon(R.drawable.menus)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menus)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle?.onOptionsItemSelected(item) == true){
            true
        }else super.onOptionsItemSelected(item)
    }

    private fun tapLogout()
    {
        btnLogOut.setOnClickListener{
            mMovieBookingModel.userLogOut(
                onSuccess = { startActivity(LoginSignUpActivity.newIntent(this)) },
                onFailure = { Snackbar.make(window.decorView,it, Snackbar.LENGTH_LONG).show() }
            )

        }

    }

    private fun showError(error: String) {
        Snackbar.make(window.decorView, error, Snackbar.LENGTH_LONG).show()
    }

}