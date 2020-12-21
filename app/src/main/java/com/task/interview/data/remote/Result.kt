package com.task.interview.data.remote

import com.task.interview.model.NetworkError
import com.task.interview.utils.fromJson
import retrofit2.HttpException

enum class ResultStatus {
    Success,
    Loading,
    Error
}

data class Result<T>(
    var response: T?,
    var error: Any?,
    var status: ResultStatus = ResultStatus.Loading
) {

    var networkError: NetworkError? = null

    companion object {
        fun <T> success(value: T?): Result<T> {
            val ans = Result(value, null, ResultStatus.Success)
            ans.code = 200
            return ans
        }

        fun <T> loading(value: T? = null) = Result(value, null, ResultStatus.Loading)
        fun <T> error(value: T? = null, error: Any?) = Result(value, error, ResultStatus.Error)
        fun <T> error(value: T? = null, error: HttpException): Result<T> {
            val ans = Result(value, error, ResultStatus.Error)
            ans.code = error.code()
            try {
                ans.networkError = error.response()?.errorBody()?.string()?.fromJson()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ans
        }
    }

    var code: Int = 0
    fun code() = code

    inline val isSuccessful get() = status == ResultStatus.Success
    inline val isLoading get() = status == ResultStatus.Loading
    inline val isError get() = status == ResultStatus.Error


}