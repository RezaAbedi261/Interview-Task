package com.task.interview.di

import com.google.gson.GsonBuilder
import com.task.interview.data.remote.AppApi
import com.task.interview.data.remote.ResponseHandler
import com.task.interview.model.Resource
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {

    factory {
        provideAppApi(retrofitInstance("https://api.foursquare.com/v2/venues/"))
    }

    factory {
        GsonBuilder().create()
    }
}

fun retrofitInstance(baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create()).build()
}


fun provideAppApi(retrofit: Retrofit): AppApi = retrofit.create(AppApi::class.java)


suspend fun <T : Any> apiCall(apiCal: suspend (AppApi.() -> T)): Resource<T> {
    return try {
        val api by inject<AppApi>()
        val response = apiCal(api)
        ResponseHandler.handleSuccess(response)
    } catch (e: Exception) {
        return ResponseHandler.handleException(e)
    }
}


