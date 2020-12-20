package com.task.interview.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaceInfo(
    val lat:Double,
    val lng:Double,
    val background_photo:String,
    val name:String,
    val type:String,
    val open:String,
    val close:String,
    val address:String,
    val photos:ArrayList<String>,
    val rate:Float,
    val options:ArrayList<String>,
    val comments:ArrayList<Comment>
) : Parcelable