package com.scissorboy.scissorboytest.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job

class MovieViewModel(
    private val movieId: String,
    private val movieThumbUrl: String,
    private val movieName: String,
    private val movieFeatured: Boolean
) : ViewModel() {
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {

    }
}