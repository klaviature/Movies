package com.example.movies.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.data.model.Review
import com.example.movies.databinding.ReviewItemBinding
import com.example.movies.formatDate
import com.example.movies.ui.adapters.MoviesRecyclerViewAdapter.OnEndReachListener

class ReviewsRecyclerViewAdapter() : RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ViewHolder>() {

    private var reviews: List<Review> = emptyList()

    private val POSITIVE = "Позитивный"
    private val NEGATIVE = "Негативный"
    private val NEUTRAL = "Нейтральный"

    private var onEndReachListener: OnEndReachListener? = null
    private var onReviewClickListener: OnReviewClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ReviewItemBinding.bind(itemView)
        val main: CardView = binding.main
        val authorTextView: TextView = binding.textViewAuthor
        val userRatingTextView: TextView = binding.textViewUserRating
        val dateTextView: TextView = binding.textViewDate
        val titleTextView: TextView = binding.textViewTitle
        val reviewTextView: TextView = binding.textViewReview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val review = reviews[position]
        holder.main.setCardBackgroundColor(
            if (review.type.equals(POSITIVE)) { ContextCompat.getColor(context, R.color.positive_review) }
            else if (review.type.equals(NEGATIVE)) { ContextCompat.getColor(context, R.color.negative_review) }
            else if (review.type.equals(NEUTRAL)) { ContextCompat.getColor(context, R.color.neutral_review) }
            else { ContextCompat.getColor(context, R.color.light_cold_gray) }
        )
        holder.authorTextView.text = review.author
        holder.userRatingTextView.text = review.userRating.toString()
        holder.dateTextView.text = formatDate(review.date)
        holder.titleTextView.text = review.title
        holder.reviewTextView.text = review.review

        holder.itemView.setOnClickListener {
            onReviewClickListener?.onReviewClick(review)
        }

        if (position == reviews.size - 1) onEndReachListener?.onEndReach()
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    fun interface OnReviewClickListener {
        fun onReviewClick(review: Review)
    }

    fun setOnEndReachListener(listener: OnEndReachListener) {
        onEndReachListener = listener
    }

    fun setOnReviewClickListener(listener: OnReviewClickListener) {
        onReviewClickListener = listener
    }

    fun setReviews(reviews: List<Review>) {
        this.reviews = reviews
        notifyDataSetChanged()
    }
}