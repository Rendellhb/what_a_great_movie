package com.scissorboy.scissorboytest.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scissorboy.scissorboytest.model.User

class MovieViewModelFactory (
    private val user: User,
    private val context: Context
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) = MovieViewModel(user, context) as T
}