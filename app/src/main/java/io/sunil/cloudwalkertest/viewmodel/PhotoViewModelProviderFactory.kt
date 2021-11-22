package io.sunil.cloudwalkertest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.sunil.cloudwalkertest.repository.PhotoRepository

class PhotoViewModelProviderFactory(
    val photoRepository: PhotoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PhotoViewModel(photoRepository) as T
    }
}