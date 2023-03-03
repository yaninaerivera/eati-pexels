package com.eati.pexels.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eati.pexels.data.PhotosRepository
import com.eati.pexels.domain.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PhotosViewModel(private val repository: PhotosRepository) : ViewModel() {

    val photosFlow = MutableStateFlow<List<Photo>>(listOf())

    fun updateResults(query: String) {
        viewModelScope.launch {
            val results = repository.getPhotos(query)
            photosFlow.emit(results)
        }
    }
}