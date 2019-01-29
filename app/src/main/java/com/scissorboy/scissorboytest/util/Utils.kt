package com.scissorboy.scissorboytest.util

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scissorboy.scissorboytest.model.Movie
import com.scissorboy.scissorboytest.model.User

fun AppCompatEditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    })
}

fun AppCompatEditText.validate(validator: (String) -> Boolean, message: String) {
    this.afterTextChanged {
        this.error = if (validator(it)) null else message
    }
    this.error = if (validator(this.text.toString())) null else message
}

fun loadJSONFromAsset(filename: String, context: Context): String {
    val root_json = context.assets.open(filename).bufferedReader().use{
        it.readText()
    }

    return root_json.replace("\n", "")
}

fun parseMovieJson(jsonString: String): List<Movie> {
    val listType = object : TypeToken<List<Movie>>() { }.type
    return Gson().fromJson<List<Movie>>(jsonString, listType)
}

class StaticObjects {
    companion object {
        @JvmStatic lateinit var user: User
    }
}
