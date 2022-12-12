package com.padcmyanmar.thiha.moviebookingapp.persistence.Daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padcmyanmar.thiha.moviebookingapp.data.vos.DataCarrierVO

@Dao
interface DataCarrierDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = DataCarrierVO::class)
    fun insertCarrierData(carrierVO: DataCarrierVO)

    @Query("SELECT * FROM booking_data WHERE id = (SELECT Max(id) FROM booking_data)")
    fun getBookingData(): DataCarrierVO?

    @Query("UPDATE booking_data SET runtime = :runtime, poster_path= :posterPath , name = :name WHERE id = (SELECT Max(id) FROM booking_data)")
    fun updateBookingDataWithMovieDetail(runtime: String, posterPath: String, name: String)

    @Query("UPDATE booking_data SET cinema_id = :cinemaId , cinema_name= :cinema_name, book_date=:bookDate, timeslot= :timeslot , timeslot_time = :timeslot_time WHERE id = (SELECT Max(id) FROM booking_data)")
    fun updateBookingDataWithTimeSlot(
        cinemaId: Int,
        cinema_name: String,
        bookDate: String,
        timeslot: Int,
        timeslot_time : String,
    )

    @Query("UPDATE booking_data SET `row`= :row, total_price= :totalPrice, seat_number= :seatNumber")
    fun updateBookingDataWithTime(row : String,totalPrice : Int, seatNumber:String)

    @Query("UPDATE booking_data SET snack = :snack , total_price=:totalPrice")
    fun updateBookingDataWithSnack(snack : String, totalPrice: Int)

    @Query("DELETE FROM booking_data")
    fun deleteBookingData()

    @Query("UPDATE booking_data SET booking_no = :bookingNo")
    fun updateBookingDataWithBookingNo(bookingNo:String)





}