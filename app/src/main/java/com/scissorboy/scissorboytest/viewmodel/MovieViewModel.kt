package com.scissorboy.scissorboytest.viewmodel

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.scissorboy.scissorboytest.R
import com.scissorboy.scissorboytest.interfaces.Webservice
import com.scissorboy.scissorboytest.interfaces.callback
import com.scissorboy.scissorboytest.interfaces.createRetrofit
import com.scissorboy.scissorboytest.model.Movie
import com.scissorboy.scissorboytest.model.User
import com.scissorboy.scissorboytest.util.NoConnectivityException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import retrofit2.HttpException
import retrofit2.Retrofit


class MovieViewModel internal constructor (
    private val user: User,
    private val context: Context
) : ViewModel() {
    private var webservice: Webservice
    private var retrofit: Retrofit = createRetrofit(context)
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
            treatExceptions(it)
        }
    }

    private val callbackFavoriteUnfavorite = callback<Movie> { response, throwable ->
        response.let {
            if (isFavorite()){
                getFavoritedMovies()
                Toast.makeText(context, R.string.movie_unfavorited, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, R.string.movie_favorited, Toast.LENGTH_SHORT).show()
            }
        }
        throwable.let {
            treatExceptions(it)
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

    fun treatExceptions(it: Throwable?) {
        if (it is NoConnectivityException)
            Toast.makeText(context, R.string.network_out_of_range, Toast.LENGTH_SHORT).show()
        else if (it is HttpException && it.code() == 404) {
            Navigation.findNavController(context as Activity, R.id.nav_host_fragment).navigate(R.id.loginFragment)
        }
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