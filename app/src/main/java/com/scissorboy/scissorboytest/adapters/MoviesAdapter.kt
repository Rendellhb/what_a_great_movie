package com.scissorboy.scissorboytest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.scissorboy.scissorboytest.MoviesListFragmentDirections
import com.scissorboy.scissorboytest.R
import com.scissorboy.scissorboytest.databinding.ListItemMovieBinding
import com.scissorboy.scissorboytest.model.Movie
import com.scissorboy.scissorboytest.viewmodel.MovieViewModel

class MoviesAdapter (private val viewModel: MovieViewModel) : ListAdapter<Movie, ViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.apply {
            val favoriteValue = when (viewModel.isFavorite()) {
                true -> holder.itemView.context.getString(R.string.unfavorite)
                false -> holder.itemView.context.getString(R.string.favorite)
            }
            bind(createDetailOnClickListener(movie), createFavoriteButtonOnClickListener(movie.movieId), movie, favoriteValue)
            itemView.tag = movie
        }
    }

    private fun createDetailOnClickListener(movie: Movie): View.OnClickListener {
        return View.OnClickListener {
            val directions = MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailFragment(movie)
            it.findNavController().navigate(directions)
        }
    }

    private fun createFavoriteButtonOnClickListener(movieId: String): View.OnClickListener {
        return when(viewModel.isFavorite()) {
            true ->
                View.OnClickListener {
                    viewModel.unfavoriteMovie(movieId)
                }
            false ->
                View.OnClickListener {
                    viewModel.favoriteMovie(movieId)
                }
        }
    }
}

class ViewHolder(
    private val binding: ListItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(itemListener: View.OnClickListener, buttonListener: View.OnClickListener, item: Movie, favoriteValue: String) {
        binding.apply {
            clickListener = itemListener
            favoriteClickListener = buttonListener
            movie = item
            this.favoriteValue = favoriteValue
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