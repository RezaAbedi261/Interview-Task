package com.task.interview.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Comment(
    val name: String,
    val text: String,
    val date: String,
    val photo: String,
    val rate: String
) : Parcelable