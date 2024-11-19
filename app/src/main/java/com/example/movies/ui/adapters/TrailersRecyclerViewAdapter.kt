package com.example.movies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.data.model.Trailer
import com.example.movies.databinding.TrailerItemBinding

class TrailersRecyclerViewAdapter() : RecyclerView.Adapter<TrailersRecyclerViewAdapter.ViewHolder>() {

    private var trailers: List<Trailer> = emptyList()

    private var onTrailerClickListener: OnTrailerClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = TrailerItemBinding.bind(itemView)
        val trailerNameTextView: TextView = binding.textViewTrailerName
    }

    fun interface OnTrailerClickListener {
        fun onTrailerClick(trailer: Trailer)
    }

    fun setOnTrailerClickListener(listener: OnTrailerClickListener) {
        onTrailerClickListener = listener
    }

    fun setTrailers(trailers: List<Trailer>) {
        this.trailers = trailers
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return trailers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trailer = trailers[position]
        holder.trailerNameTextView.text = trailer.name

        holder.itemView.setOnClickListener {
            onTrailerClickListener?.onTrailerClick(trailer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.trailer_item, parent, false)
        return ViewHolder(view)
    }
}