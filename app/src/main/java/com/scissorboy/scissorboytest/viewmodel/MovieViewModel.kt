package com.scissorboy.scissorboytest.viewmodel

import androidx.lifecycle.ViewModel

class MovieViewModel(
    private val movieId: String,
    private val movieThumbUrl: String,
    private val movieName: String,
    private val movieFeatured: Boolean
) : ViewModel() {
}