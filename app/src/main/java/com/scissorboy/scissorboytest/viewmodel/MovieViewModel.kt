package com.scissorboy.scissorboytest.viewmodel

import androidx.lifecycle.*
import com.scissorboy.scissorboytest.interfaces.Webservice
import com.scissorboy.scissorboytest.interfaces.callback
import com.scissorboy.scissorboytest.interfaces.createRetrofit
import com.scissorboy.scissorboytest.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import retrofit2.Retrofit


class MovieViewModel internal constructor (
    private val username: String
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
            webservice.getFavoriteMovies(username).enqueue(callbackImpl)
        }
        return data
    }

    fun getMovies() = movieList

    fun setMoviesGender(genderId: Int) {
        moviesGender.value = genderId
    }

    fun clearMoviesGender() {
        moviesGender.value = ALL
    }

    fun isFiltered() = moviesGender.value != ALL

    companion object {
        private const val ALL = -1
    }
}