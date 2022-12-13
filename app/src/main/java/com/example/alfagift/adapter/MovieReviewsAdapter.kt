package com.example.alfagift.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alfagift.R
import com.example.alfagift.databinding.ItemMovieReviewsBinding
import com.example.alfagift.model.MovieReviewModel
import com.example.alfagift.utils.ConstValue
import com.squareup.picasso.Picasso

class MovieReviewsAdapter: RecyclerView.Adapter<MovieReviewsAdapter.ViewHolder>() {
    private val dataList = ArrayList<MovieReviewModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMovieReviewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    fun setDataList(review: ArrayList<MovieReviewModel>){
        dataList.clear()
        dataList.addAll(review)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val viewBinding: ItemMovieReviewsBinding) : RecyclerView.ViewHolder(viewBinding.root){
        @SuppressLint("SetTextI18n")
        fun bindView(review: MovieReviewModel){
            viewBinding.apply {
                val author = review.authorDetails
                Picasso.get().load(ConstValue.BASE_URL_AVATAR + author?.avatarPath).fit().centerCrop()
                    .placeholder(R.drawable.img_bunny)
                    .error(R.drawable.error_image)
                    .into(civReviewer)
                tvReviewerName.text = author?.username
                tvReviewerRating.text = "Rating: ${author?.rating}"
                tvReview.text = "\" ${review.content} \""
            }
        }
    }
}