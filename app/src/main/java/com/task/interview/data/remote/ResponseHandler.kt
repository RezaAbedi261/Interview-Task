package com.task.interview.data.remote

import com.task.interview.model.Resource
import retrofit2.HttpException
import java.net.SocketTimeoutException

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}

object ResponseHandler {
    fun <T : Any> handleSuccess(data: T): Resource<T> {

        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(e)
            is SocketTimeoutException -> Resource.error(
                getErrorMessage(ErrorCodes.SocketTimeOut.code),
                null
            )
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "پاسخی یافت نشد (408)"
            401 -> "Unauthorised"
            404 -> "چیزی یافت نشد"
            else -> "خطا در اتصال"
        }
    }
}
