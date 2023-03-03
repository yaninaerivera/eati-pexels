package com.eati.pexels.presentation

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eati.pexels.data.PhotosRepository

object ViewModelFactory {

    val factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            PhotosViewModel(PhotosRepository())
        }
    }
}