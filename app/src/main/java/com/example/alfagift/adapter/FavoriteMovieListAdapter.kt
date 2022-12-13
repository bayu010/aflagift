package com.example.alfagift.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alfagift.R
import com.example.alfagift.databinding.ItemMoviesBinding
import com.example.alfagift.databinding.ItemMoviesFavoriteBinding
import com.example.alfagift.model.MovieModel
import com.example.alfagift.model.db.Movies
import com.example.alfagift.utils.ConstValue
import com.squareup.picasso.Picasso
import java.math.RoundingMode

class FavoriteMovieListAdapter: RecyclerView.Adapter<FavoriteMovieListAdapter.ViewHolder>() {
    private val dataList = ArrayList<Movies>()
    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMoviesFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    fun setDataList(movies: ArrayList<Movies>){
        dataList.clear()
        dataList.addAll(movies)
        notifyDataSetChanged()
    }

    fun setOnClickedCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(private val viewBinding: ItemMoviesFavoriteBinding) : RecyclerView.ViewHolder(viewBinding.root){
        @SuppressLint("SetTextI18n")
        fun bindView(movie: Movies){
            viewBinding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(movie.movieId ?: 0)
            }

            viewBinding.apply {
                val roundedRating = movie.voteRating?.toBigDecimal()?.setScale(2, RoundingMode.FLOOR)?.toString()

                Picasso.get().load(ConstValue.BASE_URL_IMAGE + movie.posterPath).fit().centerCrop()
                    .placeholder(R.drawable.img_bunny)
                    .error(R.drawable.error_image)
                    .into(ivMovieImage)
                tvTitleMovie.text = movie.movieTitle
                tvRatingMovie.text = "Rating: $roundedRating"
                tvStatusMovie.text = "Status: ${movie.statusMovie}"
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(movieId: Int)
    }

}