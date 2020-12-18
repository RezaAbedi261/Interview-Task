package com.task.interview.data.remote

import com.task.interview.model.Places
import com.task.interview.model.Resource
import com.task.interview.model.TestDbTable
import retrofit2.http.GET
import retrofit2.http.Query


interface AppApi {

    @GET("https://rezaabedi261.github.io/main.json")
    suspend fun getPlaces(): Resource<Places>

}




