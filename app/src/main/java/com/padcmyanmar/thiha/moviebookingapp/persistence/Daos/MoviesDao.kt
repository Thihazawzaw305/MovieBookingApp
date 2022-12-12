package com.padcmyanmar.thiha.moviebookingapp.persistence.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padcmyanmar.thiha.moviebookingapp.data.vos.MovieVO


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MovieVO::class)
    fun insertMovies(movies : List<MovieVO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MovieVO::class)
    fun insertSingleMovie(movie : MovieVO)

    @Query("SELECT * FROM movies WHERE type=:type")
    fun getMoviesByType(type: String): List<MovieVO>

    @Query("SELECT * FROM movies WHERE id= :id")
    fun getMovieByID(id: Int): MovieVO?


//    @Query("UPDATE movies SET imdb_rating=:imdbRating WHERE id = :movie_id")
//    fun updateImdbRating(imdbRating:Double, movie_id : Int)

    @Query("DELETE FROM movies")
    fun deleteAllMovies()
}