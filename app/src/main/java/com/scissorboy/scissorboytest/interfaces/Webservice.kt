package com.scissorboy.scissorboytest.interfaces

import android.content.Context
import com.scissorboy.scissorboytest.model.Movie
import com.scissorboy.scissorboytest.model.User
import com.scissorboy.scissorboytest.util.ConnectivityInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface Webservice {
    @GET("api/users")
    fun checkUser(@Query("username") username: String) : Call<List<User>>

    @POST("api/users.json")
    fun createUser(@Body user: User) : Call<User>

    @GET("api/movies")
    fun getMovies() : Call<List<Movie>>

    @GET("api/users/{userId}/movies")
    fun getFavoriteMovies(@Path("userId") userId: String) : Call<List<Movie>>

    @GET("/api/users/{userId}/movies/{movieId}/favorite")
    fun favoriteMovie(@Path("userId") userId: String, @Path("movieId") movieId: String): Call<Movie>

    @GET("/api/users/{userId}/movies/{movieId}/unfavorite")
    fun unfavoriteMovie(@Path("userId") userId: String, @Path("movieId") movieId: String): Call<Movie>
}

fun createRetrofit(context: Context): Retrofit {
    val client = OkHttpClient.Builder()
        .addInterceptor(ConnectivityInterceptor(context))
        .build()
    return Retrofit.Builder()
        .client(client)
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