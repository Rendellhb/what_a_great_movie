package com.scissorboy.scissorboytest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scissorboy.scissorboytest.model.User

class MovieViewModelFactory (
    private val user: User
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = MovieViewModel(user) as T
}