package com.example.movies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.data.model.Movie
import com.example.movies.databinding.MovieItemBinding

class MoviesRecyclerViewAdapter() :
    RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder>() {

    private var movies: List<Movie> = emptyList()

    private var onEndReachListener: OnEndReachListener? = null
    private var onMovieClickListener: OnMovieClickListener? = null

    fun setMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = MovieItemBinding.bind(itemView)
        val posterImageView: ImageView
        val ratingKPTextView: TextView
        val ratingIMDbTextView: TextView

        init {
            posterImageView = binding.imageViewPoster
            ratingKPTextView = binding.ratingKp
            ratingIMDbTextView = binding.ratingImdb
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val movie = movies[position]
        Glide.with(holder.itemView)
            .load(movie.poster?.url)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.posterImageView)

        holder.ratingKPTextView.text = String.format("%.1f", movie.rating?.kp ?: 0.0)
        holder.ratingIMDbTextView.text = String.format("%.1f", movie.rating?.imdb ?: 0.0)
        when (movie.rating?.kp ?: 0.0) {
            in 7.0..10.0 -> holder.ratingKPTextView.background =
                ContextCompat.getDrawable(context, R.drawable.movie_card_rating_high_background)

            in 4.0..7.0 -> holder.ratingKPTextView.background =
                ContextCompat.getDrawable(context, R.drawable.movie_card_rating_medium_background)

            in 0.0..4.0 -> holder.ratingKPTextView.background =
                ContextCompat.getDrawable(context, R.drawable.movie_card_rating_low_background)
        }

        holder.itemView.setOnClickListener {
            onMovieClickListener?.onMovieClick(movie)
        }

        if (position == movies.size - 1) onEndReachListener?.onEndReach()
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setOnEndReachListener(listener: OnEndReachListener) {
        onEndReachListener = listener
    }

    fun setOnMovieClickListener(listener: OnMovieClickListener) {
        onMovieClickListener = listener
    }

    fun interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    fun interface OnEndReachListener {
        fun onEndReach()
    }
}