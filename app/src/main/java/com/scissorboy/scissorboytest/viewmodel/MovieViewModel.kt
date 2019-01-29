package com.scissorboy.scissorboytest.viewmodel

import androidx.lifecycle.*
import com.scissorboy.scissorboytest.interfaces.Webservice
import com.scissorboy.scissorboytest.interfaces.callback
import com.scissorboy.scissorboytest.interfaces.createRetrofit
import com.scissorboy.scissorboytest.model.Movie
import com.scissorboy.scissorboytest.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import retrofit2.Retrofit


class MovieViewModel internal constructor (
    private val user: User
) : ViewModel() {
    private var webservice: Webservice
    private var retrofit: Retrofit = createRetrofit()
    private val movieList = MediatorLiveData<List<Movie>>()
    private val data = MutableLiveData<List<Movie>>()
    private val moviesGender = MutableLiveData<Int>()
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Main + viewModelJob)

    private val callbackImpl = callback<List<Movie>> { response, throwable ->
        response.let {
            data.value = response?.body()
        }
        throwable.let {
            if (it != null) data.value = ArrayList()
        }
    }

    private val callbackFavoriteUnfavorite = callback<Movie> { response, throwable ->
        response.let {
            if (isFavorite()) getFavoritedMovies()
        }
        throwable.let {

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        moviesGender.value = ALL
        webservice = retrofit.create(Webservice::class.java)
        val liveMoviesList = Transformations.switchMap(moviesGender) {
            //TODO: Implement filter
            if (it == ALL) {
                getAllMovies()
            } else {
                getFavoritedMovies()
            }

        }

        movieList.addSource(liveMoviesList, movieList::setValue)
    }

    fun getAllMovies() : LiveData<List<Movie>> {
        viewModelScope.run {
            webservice.getMovies().enqueue(callbackImpl)

            return data
        }
    }

    fun getFavoritedMovies() : LiveData<List<Movie>> {
        viewModelScope.run {
            webservice.getFavoriteMovies(user.id!!).enqueue(callbackImpl)

            return data
        }
    }

    fun favoriteMovie(movieId: String) {
        viewModelScope.run {
            webservice.favoriteMovie(user.id!!, movieId).enqueue(callbackFavoriteUnfavorite)
        }
    }

    fun unfavoriteMovie(movieId: String) {
        viewModelScope.run {
            webservice.unfavoriteMovie(user.id!!, movieId).enqueue(callbackFavoriteUnfavorite)
        }
    }

    fun getMovies() = movieList

    fun setMoviesGender(genderId: Int) {
        moviesGender.value = genderId
    }

    fun clearMoviesGender() {
        moviesGender.value = ALL
    }

    fun isFavorite() = moviesGender.value != ALL

    companion object {
        const val ALL = -1
        const val FAVORITED = 1
    }
}