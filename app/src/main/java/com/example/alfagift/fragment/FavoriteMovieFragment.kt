package com.example.alfagift.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.alfagift.R
import com.example.alfagift.activity.MovieInfoActivity
import com.example.alfagift.adapter.FavoriteMovieListAdapter
import com.example.alfagift.adapter.MovieListAdapter
import com.example.alfagift.databinding.FragmentFavoriteMoviesBinding
import com.example.alfagift.databinding.LayoutMovieListBinding
import com.example.alfagift.db.MoviesDatabase
import com.example.alfagift.fragment.core.CoreFragment
import com.example.alfagift.model.db.Movies
import com.example.alfagift.model.util.Resource
import com.example.alfagift.utils.MessageDialog
import com.example.alfagift.utils.ProgressDialog
import com.example.alfagift.viewmodel.TopRatedViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteMovieFragment: CoreFragment() {
    override val viewBinding by lazy { FragmentFavoriteMoviesBinding.inflate(layoutInflater) }
    private val adapter = FavoriteMovieListAdapter()
    private lateinit var dbMovie: MoviesDatabase
    var movies: ArrayList<Movies> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initView()
        getData()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun getData(){
        dbMovie = Room.databaseBuilder(requireContext(), MoviesDatabase::class.java, "movies-db").build()

        GlobalScope.launch {
            movies = dbMovie.moviesDao().getAllMovies() as ArrayList<Movies>
            if (movies.isEmpty()){
                viewBinding.rvMovieList.visibility = View.GONE
                viewBinding.tvEmptyText.visibility = View.VISIBLE
            }else{
                adapter.setDataList(movies)
            }
        }
    }

    private fun initView(){
        viewBinding.tbFavoriteMovie.toolbarTitle.text = resources.getString(R.string.text_fav_movie)
        viewBinding.tbFavoriteMovie.ivFavorite.visibility = View.GONE

        viewBinding.tvMovieCategory.text = resources.getString(R.string.text_favorite_movies)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        viewBinding.apply {
            rvMovieList.layoutManager = layoutManager
            rvMovieList.setHasFixedSize(true)
            rvMovieList.adapter = adapter
        }

        adapter.setOnClickedCallback(object: FavoriteMovieListAdapter.OnItemClickCallback{
            override fun onItemClicked(movieId: Int) {
                MovieInfoActivity.launchIntent(requireContext(), movieId = movieId)
            }
        })
    }

    companion object {
        fun newInstance(): FavoriteMovieFragment {
            return FavoriteMovieFragment()
        }
    }
}