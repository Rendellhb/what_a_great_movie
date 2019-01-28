package com.scissorboy.scissorboytest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scissorboy.scissorboytest.MoviesListFragmentDirections
import com.scissorboy.scissorboytest.databinding.ListItemMovieBinding
import com.scissorboy.scissorboytest.model.Movie

class MoviesAdapter : ListAdapter<Movie, ViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.apply {
            bind(createDetailOnClickListener(movie.movieId), movie)
            itemView.tag = movie
        }
    }

    private fun createDetailOnClickListener(movieId: String): View.OnClickListener {
        return View.OnClickListener {
            val directions = MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailFragment()
            it.findNavController().navigate(directions)
        }
    }

    private fun createFavoriteOnClickListener(moviesId: String): View.OnClickListener {
        return View.OnClickListener {
            //TODO Create a favorited movie
        }
    }
}

class ViewHolder(
    private val binding: ListItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(listener: View.OnClickListener, item: Movie) {
        binding.apply {
            clickListener = listener
            movie = item
            executePendingBindings()
        }
    }
}

private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}