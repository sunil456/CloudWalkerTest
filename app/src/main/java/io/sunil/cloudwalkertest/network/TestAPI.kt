package io.sunil.cloudwalkertest.network

import io.sunil.cloudwalkertest.model.Photo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TestAPI {

    @GET("photos")
    suspend fun getPhotos() : Response<List<Photo>>

    @GET("photos/{id}")
    suspend fun getUserPhotos(@Path("id") userId: Int) : Response<Photo>
}