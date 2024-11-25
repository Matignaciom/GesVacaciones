package com.iplacex.gesvacacionesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place")
data class Place(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val visitOrder: Int,
    val imageUrl: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val lodgingCost: Double? = null,
    val transportCost: Double? = null,
    val comments: String? = null,
    val photoUri: String? = null
)
