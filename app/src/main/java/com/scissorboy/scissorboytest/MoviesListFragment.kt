package com.scissorboy.scissorboytest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.scissorboy.scissorboytest.adapters.MoviesAdapter
import com.scissorboy.scissorboytest.databinding.FragmentMoviesListBinding
import com.scissorboy.scissorboytest.util.StaticObjects
import com.scissorboy.scissorboytest.viewmodel.MovieViewModel
import com.scissorboy.scissorboytest.viewmodel.MovieViewModelFactory
import kotlinx.android.synthetic.main.main_activity.*

class MoviesListFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        val user = StaticObjects.user
        val factory = MovieViewModelFactory(user, requireContext())
        viewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)

        setHasOptionsMenu(true)

        val adapter = MoviesAdapter(viewModel)
        binding.movieList.adapter = adapter
        subscribeUi(adapter)

        val mainActivity = requireActivity() as MainActivity
        if (user.username.isNotEmpty()) mainActivity.supportActionBar?.title = getString(R.string.welcome_home, user.username)
        mainActivity.navigation.visibility = View.VISIBLE
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.clearMoviesGender()
    }

    private fun subscribeUi(adapter: MoviesAdapter) {
        viewModel.getMovies().observe(viewLifecycleOwner, Observer { movies ->
            if (movies != null) adapter.submitList(movies)
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.logout)?.isVisible = true
        super.onPrepareOptionsMenu(menu)
    }
}
