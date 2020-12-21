package com.task.interview.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class PlaceInfo(
    @PrimaryKey
    val lat:Double,
    val lng:Double,
    val background_photo:String,
    val name:String,
    val type:String,
    val open:String,
    val close:String,
    val address:String,
    val rate:Float,
    @TypeConverters(CategoryConverter::class)val photos:ArrayList<String>?,
    @TypeConverters(CategoryConverter::class)val options:ArrayList<String>?,
    @TypeConverters(CategoryConverter::class) val comments:ArrayList<Comment>
) : Parcelable