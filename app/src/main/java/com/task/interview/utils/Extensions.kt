package com.task.interview.utils

import com.google.gson.Gson
import java.lang.reflect.Type

val Any.json: String get() = Gson().toJson(this)

fun <T> String.fromJson(cls: Type): T? {

    try {
        return Gson().fromJson<T>(this, cls)
    } catch (e: Exception) {
    }
    return null
}

inline fun <reified T> String.fromJson(): T? {
    return fromJson(T::class.javaClass)
}
