package com.scissorboy.scissorboytest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scissorboy.scissorboytest.interfaces.Webservice
import com.scissorboy.scissorboytest.interfaces.callback
import com.scissorboy.scissorboytest.interfaces.createRetrofit
import com.scissorboy.scissorboytest.model.User
import com.scissorboy.scissorboytest.util.StaticObjects
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import retrofit2.Retrofit

class LoginViewModel internal constructor(
) : ViewModel() {
    private var username: String = ""
    private var webservice: Webservice
    private var retrofit: Retrofit = createRetrofit()
    private val user = MediatorLiveData<List<User>>()
    private val data = MutableLiveData<List<User>>()
    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val getUserCallback = callback<List<User>> { response, throwable ->
        response.let {
            data.value = response?.body()
            if (data.value!!.isNotEmpty() && data.value!!.size == 1) StaticObjects.user = data.value!![0]
        }
        throwable.let {
            if (it != null) {
                data.value = null
                username = ""
            }
        }
    }

    private val createUserCallback = callback<User> { response, throwable ->
        response.let {
            val users = ArrayList<User>()
            response?.body()?.let { it1 -> users.add(it1) }
            StaticObjects.user = users[0]
            data.value = users
        }
        throwable.let {
            if (it != null) data.value = null
        }
    }

    init {
        webservice = retrofit.create(Webservice::class.java)
        getUser()
        user.addSource(data, user::setValue)
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun getUser() : LiveData<List<User>> {
        viewModelScope.run {
            webservice.checkUser(username).enqueue(getUserCallback)

            return data
        }
    }

    fun createUser() : LiveData<List<User>> {
        viewModelScope.run {
            val user = User(null, username, null, null, null)
            webservice.createUser(user).enqueue(createUserCallback)

            return data
        }
    }

    fun clearUser() {
        StaticObjects.user = User(null, "" , null, null, null)
    }
}