package com.task.interview.data.remote

import com.task.interview.model.Places
import retrofit2.HttpException
import retrofit2.http.GET


interface AppApi {

    @GET("main.json")
    suspend fun getPlaces(): Places

}


suspend fun <T> apiCall(apiFun: suspend AppApi.() -> T): Result<T> {
    return try {
        Result.success(apiFun(RetrofitFactory.retrofitService))
    } catch (e: HttpException) {
        e.printStackTrace()
        Result.error(null, e)
    } catch (e: Exception) {
        e.printStackTrace()
        Result.error(null, e)
    }
}




