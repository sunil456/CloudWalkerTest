package io.sunil.cloudwalkertest.utils

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val statusCode: Int? = null
) {

    class Success<T>(data: T?) : Resource<T>(data)

    class  Error<T>(message: String, statusCode: Int) : Resource<T>(data = null,message, statusCode)

    class Loading<T> : Resource<T>()

}