package com.scissorboy.scissorboytest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scissorboy.scissorboytest.adapters.MoviesAdapter
import com.scissorboy.scissorboytest.databinding.FragmentHomeBinding
import com.scissorboy.scissorboytest.model.Movie
import com.scissorboy.scissorboytest.util.StaticObjects
import com.scissorboy.scissorboytest.util.loadJSONFromAsset
import com.scissorboy.scissorboytest.viewmodel.MovieViewModel
import com.scissorboy.scissorboytest.viewmodel.MovieViewModelFactory
import kotlinx.android.synthetic.main.main_activity.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        val username = StaticObjects.username
        val jsonString = loadJSONFromAsset("movies.json", requireActivity())
        val movies = parseMovieJson(jsonString)
        val factory = MovieViewModelFactory(movies)
        viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)

        val adapter = MoviesAdapter()
        binding.movieList.adapter = adapter
        subscribeUi(adapter)

        val mainActivity = requireActivity() as MainActivity
        if (!username.isEmpty()) mainActivity.supportActionBar?.title = getString(R.string.welcome_home, username)
        mainActivity.navigation.visibility = View.VISIBLE
        return binding.root
    }

    private fun subscribeUi(adapter: MoviesAdapter) {
        viewModel.getMovies().observe(viewLifecycleOwner, Observer { movies ->
            if (movies != null) adapter.submitList(movies)
        })
    }

    private fun updateData(movieGender: Int) {
        with(viewModel) {
            if (isFiltered()) {
                clearMoviesGender()
            } else {
                setMoviesGender(movieGender)
            }
        }
    }

    private fun parseMovieJson(jsonString: String): List<Movie> {
        val listType = object : TypeToken<List<Movie>>() { }.type
        return Gson().fromJson<List<Movie>>(jsonString, listType)
    }
}
