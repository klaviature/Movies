package com.example.movies.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movies.ui.viewmodel.WatchedViewModel
import com.example.movies.databinding.FragmentWatchedBinding
import com.example.movies.ui.adapters.MoviesRecyclerViewAdapter

class WatchedFragment : Fragment() {

    private val LOG_TAG = "WatchedFragment"

    private lateinit var binding: FragmentWatchedBinding

    private val viewModel: WatchedViewModel by viewModels()

    private val adapter = MoviesRecyclerViewAdapter()

    companion object {
        fun newInstance() = WatchedFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesRecyclerView.adapter = adapter
        adapter.setOnMovieClickListener { movie ->
            val intent = MovieDetailActivity.newIntent(requireContext(), movie.id)
            Log.d(LOG_TAG, movie.id.toString())
            startActivity(intent)
        }

        viewModel.getMovies().observe(viewLifecycleOwner) { movies ->
            adapter.setMovies(movies)
        }
    }
}