package io.sunil.cloudwalkertest.repository

import io.sunil.cloudwalkertest.network.RetrofitInstance

class PhotoRepository {

    suspend fun getPhotos() = RetrofitInstance.api.getPhotos()

    suspend fun getUsersPhotos(id :Int) = RetrofitInstance.api.getUserPhotos(id)
}