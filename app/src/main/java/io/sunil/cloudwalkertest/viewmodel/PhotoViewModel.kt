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



            withContext(Dispatchers.IO){
                val multipleIds = listOf(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,12,22,23,24,25,26,27,28,29,30)
                val content = arrayListOf<Photo>()

                val runningTask = multipleIds.map { id ->
                    async {
                        val apiResponse = photoRepository.getUsersPhotos(id)
                        id to apiResponse

                    }
                }

                val response = runningTask.awaitAll()




                response.forEach { (_, response) ->
                    if (response.isSuccessful)
                    {

                        response.body()?.let {
                            content.addAll(listOf(it))

                        }
                    }
                }
                userPhotos.postValue(Resource.Success(content))
            }


//            try {
//
//                val mutableListOf = listOf(0..50)
//                coroutineScope {
//
//                    for ( i in 1..50)
//                    {
//                        usersFromApiDeferred = async { photoRepository.getUsersPhotos(i) }
//                    }
//
//
//                    val usersFromApi = usersFromApiDeferred.await()
//                    if (usersFromApi.isSuccessful)
//                    {
//                        usersFromApi.body()?.let {
//                            val allUsersFromApi = mutableListOf<Photo>()
//                            allUsersFromApi.toMutableList().addAll(listOf(it))
//
//                            userPhotos.postValue(Resource.Success(allUsersFromApi))
//                        }
//                    }
//                    else{
//                        userPhotos.postValue(Resource.Error(usersFromApi.message(), usersFromApi.code()))
//                    }
//                }
//            }
//            catch (e: Exception) {
//                userPhotos.postValue(Resource.Error("Something Went Wrong", hashCode()))
//            }
        }
    }

}