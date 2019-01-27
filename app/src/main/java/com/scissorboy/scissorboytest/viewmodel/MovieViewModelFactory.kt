package com.scissorboy.scissorboytest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieViewModelFactory (
    private val movieId: String,
    private val movieThumbUrl: String,
    private val movieName: String,
    private val movieFeatured: Boolean
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(movieId, movieThumbUrl, movieName, movieFeatured) as T
    }
}