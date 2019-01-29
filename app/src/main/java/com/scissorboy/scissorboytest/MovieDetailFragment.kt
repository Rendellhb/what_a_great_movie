package com.scissorboy.scissorboytest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.scissorboy.scissorboytest.databinding.FragmentMovieDetailBinding
import com.scissorboy.scissorboytest.model.Genres
import kotlinx.android.synthetic.main.main_activity.*


class MovieDetailFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        val movie = MovieDetailFragmentArgs.fromBundle(arguments!!).movie

        val genres = movie.genres

        val listAdapter = ArrayAdapter<Genres>(requireContext(), android.R.layout.simple_list_item_1, genres)
        binding.movieGender.adapter = listAdapter

        binding.apply {
            this.movie = movie
            executePendingBindings()
        }

        setHasOptionsMenu(true)
        val mainActivity = requireActivity() as MainActivity
        mainActivity.supportActionBar?.title = ""

        return binding.root
    }

    override fun onResume() {
        requireActivity().navigation.visibility = View.GONE
        super.onResume()
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.logout)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        requireActivity().navigation.visibility = View.VISIBLE
        super.onPause()
    }
}