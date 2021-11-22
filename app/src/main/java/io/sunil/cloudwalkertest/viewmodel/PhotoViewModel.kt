package io.sunil.cloudwalkertest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.sunil.cloudwalkertest.model.Photo
import io.sunil.cloudwalkertest.repository.PhotoRepository
import io.sunil.cloudwalkertest.utils.Resource
import kotlinx.coroutines.*
import retrofit2.Response

class PhotoViewModel(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    val photos: MutableLiveData<Resource<List<Photo>>> = MutableLiveData()
    val userPhotos = MutableLiveData<Resource<List<Photo>>>()

    lateinit var usersFromApiDeferred: Deferred<Response<Photo>>

    init {

        getPhotos()
//        fetchUsersPhotos()
    }

    fun getPhotos() = viewModelScope.launch {

        photos.postValue(Resource.Loading())
        val response = photoRepository.getPhotos()

        photos.postValue(handlePhotosResponse(response))

    }

    private fun handlePhotosResponse(response : Response<List<Photo>>) : Resource<List<Photo>>{

        if (response.isSuccessful)
        {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.message(), response.code())

    }

    fun fetchUsersPhotos()
    {
        viewModelScope.launch {
            userPhotos.postValue(Resource.Loading())


            try {

                coroutineScope {

                    for ( i in 1..50)
                    {
                        usersFromApiDeferred = async { photoRepository.getUsersPhotos(i) }
                    }


                    withContext(Dispatchers.IO){
                        val usersFromApi = usersFromApiDeferred.await()
                        if (usersFromApi.isSuccessful)
                        {
                            usersFromApi.body()?.let {
                                val allUsersFromApi = mutableListOf<Photo>()
                                allUsersFromApi.toMutableList().addAll(listOf(it))

                                userPhotos.postValue(Resource.Success(allUsersFromApi))
                            }
                        }

                        userPhotos.postValue(Resource.Error(usersFromApi.message(), usersFromApi.code()))
                    }






                }
            }
            catch (e: Exception) {
                userPhotos.postValue(Resource.Error("Something Went Wrong", hashCode()))
            }
        }
    }

}