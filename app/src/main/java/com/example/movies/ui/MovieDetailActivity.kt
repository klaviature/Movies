package com.example.movies.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.data.model.Movie
import com.example.movies.databinding.ActivityMovieDetailBinding
import com.example.movies.ui.adapters.ReviewsRecyclerViewAdapter
import com.example.movies.ui.adapters.TrailersRecyclerViewAdapter
import com.example.movies.ui.viewmodel.MovieDetailViewModel
import androidx.core.view.WindowCompat
import androidx.core.view.updatePadding


class MovieDetailActivity : AppCompatActivity() {

    private val TAG = "MovieDetailActivity"

    private lateinit var binding: ActivityMovieDetailBinding

    private lateinit var viewModel: MovieDetailViewModel

    private lateinit var reviewsAdapter: ReviewsRecyclerViewAdapter
    private lateinit var trailersAdapter: TrailersRecyclerViewAdapter

    private val reviewFragment = ReviewFragment()

    private var id: Int = -1;
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setInsets()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        setupObservers()

        setupAdapters()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadMovie(id)
        }

        binding.addToFav.setOnClickListener {
            movie?.let { it1 ->
                viewModel.toggleFavourite(it1) { inFavourite ->
                    if (inFavourite) {
                        Toast
                            .makeText(this, "Фильм добавлен в избранное", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast
                            .makeText(this, "Фильм удален из избранного", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        binding.addToWatched.setOnClickListener {
            movie?.let { it1 ->
                viewModel.toggleWatched(it1) { inWatched ->
                    if (inWatched) {
                        Toast
                            .makeText(this, "Фильм добавлен в просмотренное", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast
                            .makeText(this, "Фильм удален из просмотренного", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        id = intent.getIntExtra(EXTRA_MOVIE_ID, -1)
        viewModel.loadMovie(id)
    }

    private fun setupAdapters() {
        reviewsAdapter = ReviewsRecyclerViewAdapter()
        findViewById<RecyclerView>(R.id.reviewsRecyclerView).adapter = reviewsAdapter
        reviewsAdapter.setOnEndReachListener {
            viewModel.loadReviewsNextPage()
        }
        reviewsAdapter.setOnReviewClickListener { review ->
            reviewFragment.review = review
            reviewFragment.show(supportFragmentManager, ReviewFragment.TAG)
        }

        trailersAdapter = TrailersRecyclerViewAdapter()
        findViewById<RecyclerView>(R.id.trailersRecyclerView).adapter = trailersAdapter
        trailersAdapter.setOnTrailerClickListener { trailer ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(trailer.url)
            startActivity(intent)
        }
    }

    private fun setupObservers() {

        viewModel.movie.observe(this) { movie ->
            this.movie = movie
            bindMovieData()
            this.movie?.videos?.trailers?.let { trailersAdapter.setTrailers(it.distinct()) }
        }

        viewModel.isInFavourites.observe(this) { inFavourites ->
            when (inFavourites) {
                true -> { binding.addToFav.setImageResource(R.drawable.star_filled) }
                else -> { binding.addToFav.setImageResource(R.drawable.star_outlined) }
            }
        }

        viewModel.isInWatched.observe(this) { inWatched ->
            when (inWatched) {
                true -> binding.addToWatched.setImageResource(R.drawable.eye)
                else -> binding.addToWatched.setImageResource(R.drawable.eye_slashed)
            }
        }

        viewModel.reviews.observe(this) { reviewsApi ->
            reviewsAdapter.setReviews(reviewsApi)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }
    }

    private fun bindMovieData() {
        Glide.with(this)
            .load(movie?.poster?.url ?: R.color.black)
            .placeholder(R.color.black)
            .into(binding.imageViewPoster)
        binding.textViewMovieName.text = movie?.name ?: movie?.alternativeName
        binding.textViewRating.text = String.format("%.1f", movie?.rating?.kp ?: 0.0)
        binding.ratingImdb.text = String.format("%.1f", movie?.rating?.imdb ?: 0.0)
        when (movie?.rating?.kp ?: 0.0) {
            in 7.0..10.0 -> binding.textViewRating.background =
                ContextCompat.getDrawable(this, R.drawable.movie_card_rating_high_background)

            in 4.0..7.0 -> binding.textViewRating.background =
                ContextCompat.getDrawable(this, R.drawable.movie_card_rating_medium_background)

            in 0.0..4.0 -> binding.textViewRating.background =
                ContextCompat.getDrawable(this, R.drawable.movie_card_rating_low_background)
        }
        binding.textViewMovieYear.text = movie?.year.toString()
        binding.textViewMovieLength.text = "${movie?.movieLength ?: 0} мин"
        binding.textViewMovieCountries.text = movie?.countries?.joinToString(separator = ", ")
        binding.textViewMovieGenres.text = movie?.genres?.joinToString(separator = ", ")
        binding.textViewMovieAgeRating.text = "${movie?.ageRating ?: 0}+"
        binding.textViewMovieDescription.text = movie?.description
    }

    private fun setInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.scrollView) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(
                bottom = insets.bottom
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    companion object {
        const val EXTRA_MOVIE_ID = "movie_id"
        fun newIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE_ID, id)
            return intent
        }
    }
}