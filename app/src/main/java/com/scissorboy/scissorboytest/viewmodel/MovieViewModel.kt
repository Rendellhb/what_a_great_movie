package com.scissorboy.scissorboytest.viewmodel

import androidx.lifecycle.*
import com.scissorboy.scissorboytest.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job

class MovieViewModel internal constructor (
    private val movies: List<Movie>
) : ViewModel() {
    private val movieList = MediatorLiveData<List<Movie>>()
    private val moviesGender = MutableLiveData<Int>()
    private val moviesLiveData = MutableLiveData<List<Movie>>()
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        moviesGender.value = ALL
        val liveMoviesList = Transformations.switchMap(moviesGender) {
            //TODO: Implement filter
//            if (it == ALL) {
//                moviesLiveData
//            } else {
//                moviesLiveData
//            }
            getLiveDataMovies(movies)
        }

        movieList.addSource(liveMoviesList, movieList::setValue)
    }

    fun getMovies() = movieList

    fun getLiveDataMovies(movies: List<Movie>) : LiveData<List<Movie>> {
        moviesLiveData.value = movies
        return moviesLiveData
    }

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