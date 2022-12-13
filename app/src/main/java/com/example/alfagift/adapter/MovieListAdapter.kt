package com.example.alfagift.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alfagift.R
import com.example.alfagift.databinding.ItemMoviesBinding
import com.example.alfagift.model.MovieModel
import com.example.alfagift.utils.ConstValue
import com.squareup.picasso.Picasso

class MovieListAdapter: RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private val dataList = ArrayList<MovieModel>()
    private var onItemClickCallback: OnItemClickCallback? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    fun setDataList(movies: ArrayList<MovieModel>){
        dataList.clear()
        dataList.addAll(movies)
        notifyDataSetChanged()
    }

    fun setOnClickedCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ViewHolder(private val viewBinding: ItemMoviesBinding) : RecyclerView.ViewHolder(viewBinding.root){
        fun bindView(movie: MovieModel){
            viewBinding.btnDetailMovie.setOnClickListener {
                onItemClickCallback?.onItemClicked(movie.movieId ?: 0)
            }

            viewBinding.apply {
                Picasso.get().load(ConstValue.BASE_URL_IMAGE + movie.posterPath).fit().centerCrop()
                    .placeholder(R.drawable.img_bunny)
                    .error(R.drawable.error_image)
                    .into(ivMovieImage)
                tvTitleMovie.text = movie.movieTitle
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(movieId: Int)
    }

}