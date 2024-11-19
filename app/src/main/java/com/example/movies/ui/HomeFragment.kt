package com.example.movies.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.example.movies.ui.viewmodel.HomeViewModel
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.ui.adapters.MoviesRecyclerViewAdapter

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val LOG_TAG = "HomeFragment"

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private val adapter: MoviesRecyclerViewAdapter = MoviesRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movies.observe(viewLifecycleOwner) { movies ->
            adapter.setMovies(movies)
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            val query = binding.searchView.query
            viewModel.loadMovies(query.toString())
        }

        binding.moviesRecyclerView.adapter = adapter
        adapter.setOnEndReachListener {
            viewModel.loadNextPage()
        }
        adapter.setOnMovieClickListener { movie ->
            val intent = MovieDetailActivity.newIntent(requireContext(), movie.id)
            startActivity(intent)
        }

        setSearchViewListeners()

        viewModel.loadMovies()
    }

    private fun setSearchViewListeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                viewModel.loadMovies(query)
                return true
            }
        })
        binding.searchView.setOnCloseListener {
            binding.searchView.onActionViewCollapsed()
            viewModel.loadMovies()
            true
        }
    }
}