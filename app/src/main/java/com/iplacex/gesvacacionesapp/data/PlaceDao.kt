package com.iplacex.gesvacacionesapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    @Query("SELECT * FROM place ORDER BY visitOrder ASC")
    fun getAllPlaces(): Flow<List<Place>>

    @Query("SELECT * FROM place WHERE id = :id LIMIT 1")
    fun getPlaceById(id: Long): Flow<Place>

    @Query("SELECT * FROM place WHERE visitOrder > :order")
    fun getPlacesAfterOrder(order: Int): Flow<List<Place>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: Place): Long

    @Update
    suspend fun updatePlace(place: Place): Int

    @Delete
    suspend fun deletePlace(place: Place)
}
