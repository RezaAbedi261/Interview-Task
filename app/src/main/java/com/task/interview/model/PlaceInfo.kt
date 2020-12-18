package com.task.interview.model

data class PlaceInfo(
    val lat:Long,
    val lng:Long,
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
)