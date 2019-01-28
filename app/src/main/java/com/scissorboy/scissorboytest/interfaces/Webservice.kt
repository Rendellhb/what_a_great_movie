package com.scissorboy.scissorboytest.interfaces

import com.scissorboy.scissorboytest.model.Movie
import com.scissorboy.scissorboytest.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface Webservice {
    @GET("api/users?username={username}")
    fun checkUser(@Path("username") username: String) : Call<User>

    @GET("api/movies")
    fun getMovies() : Call<List<Movie>>

    @GET("api/users/{username}/movies")
    fun getFavoriteMovies(@Path("username") username: String) : Call<List<Movie>>
}


fun createRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://46.101.218.241/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun <T> callback(callResponse: (response: Response<T>?,
                                throwable: Throwable?) -> Unit): Callback<T> {
    return object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            callResponse(response, null)
        }

        override fun onFailure(call: Call<T>?, t: Throwable?) {
            callResponse(null, t)
        }
    }
}