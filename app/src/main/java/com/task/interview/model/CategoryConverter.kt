package com.task.interview.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class CategoryConverter {

    var gson = Gson()

    @TypeConverter
    fun stringToCategoryList(data: String?): ArrayList<String?>? {
        if (data == null) {
            return ArrayList<String?>()
        }
        val listType = object : TypeToken<ArrayList<String?>?>() {}.type
        return gson.fromJson<ArrayList<String?>>(data, listType)
    }

    @TypeConverter
    fun listToString(list: List<String?>?): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToCommentsList(data: String?): ArrayList<Comment?>? {
        if (data == null) {
            return ArrayList<Comment?>()
        }
        val listType = object : TypeToken<ArrayList<Comment?>?>() {}.type
        return gson.fromJson<ArrayList<Comment?>>(data, listType)
    }

    @TypeConverter
    fun commentsListToString(list: List<Comment?>?): String? {
        return gson.toJson(list)
    }


}