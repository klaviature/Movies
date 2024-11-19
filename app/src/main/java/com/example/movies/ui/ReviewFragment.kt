package com.example.movies.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movies.data.model.Review
import com.example.movies.databinding.FragmentReviewBinding
import com.example.movies.formatDate
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReviewFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentReviewBinding

    var review: Review? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        review?.let {
            binding.textViewAuthor.text = review!!.author
            binding.likes.text = review!!.likes.toString()
            binding.dislikes.text = review!!.dislikes.toString()
            binding.textViewDate.text = formatDate(review!!.date)
            binding.textViewType.text = review!!.type
            binding.textViewTitle.text = review!!.title
            binding.textViewReview.text = review!!.review
        }

    }

    override fun onCreateDialog(
        savedInstanceState: Bundle?,
    ): Dialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.getBehavior().peekHeight = 1200
        return bottomSheetDialog
    }

    companion object {
        const val TAG = "ReviewFragment"
    }
}