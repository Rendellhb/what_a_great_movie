package com.scissorboy.scissorboytest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scissorboy.scissorboytest.model.Movie

class MovieViewModelFactory (
    private val movies: List<Movie>
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = MovieViewModel(movies) as T
}