package com.example.alfagift.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alfagift.R
import com.example.alfagift.activity.MovieInfoActivity
import com.example.alfagift.activity.ReviewsActivity.Companion.KEY_MOVIE_ID
import com.example.alfagift.adapter.MovieReviewsAdapter
import com.example.alfagift.databinding.FragmentFavoriteMoviesBinding
import com.example.alfagift.fragment.core.CoreFragment
import com.example.alfagift.model.util.Resource
import com.example.alfagift.utils.MessageDialog
import com.example.alfagift.utils.ProgressDialog
import com.example.alfagift.viewmodel.MovieReviewsViewModel

class MovieReviewsFragment: CoreFragment() {
    override val viewBinding by lazy { FragmentFavoriteMoviesBinding.inflate(layoutInflater) }
    private val viewModel by lazy { MovieReviewsViewModel() }
    private val adapter = MovieReviewsAdapter()
    private val movieId by lazy { arguments?.getInt(KEY_MOVIE_ID)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
        initRecyclerView()
        if (movieId != null || movieId != 0){
            viewModel.getMovieReviews(movieId!!)
        }else{
            MessageDialog.show(
                context = requireContext(),
                title = resources.getString(R.string.label_error),
                message = "Movie ID is not found"
            )
        }
    }

    private fun initViewModel(){
        viewModel.state.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.LOADING -> {
                    ProgressDialog.show(requireContext())
                }
                Resource.SUCCESS -> {
                    ProgressDialog.dismiss()
                }
                Resource.ERROR -> {
                    ProgressDialog.dismiss()
                    MessageDialog.show(
                        context = requireContext(),
                        title = resources.getString(R.string.failure),
                        message = it.errorMessage
                    )
                }
            }
        }

        viewModel.dataList.observe(viewLifecycleOwner){
            if (it.results != null){
                adapter.setDataList(it.results)
            }else{
                viewBinding.rvMovieList.visibility = View.GONE
                viewBinding.tvEmptyText.visibility = View.VISIBLE
            }
        }
        lifecycle.addObserver(viewModel)
    }

    private fun initView(){
        viewBinding.tbFavoriteMovie.toolbarTitle.text = resources.getString(R.string.text_review)
        viewBinding.tbFavoriteMovie.ivFavorite.visibility = View.GONE
        viewBinding.tvMovieCategory.visibility = View.GONE
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        viewBinding.apply {
            rvMovieList.layoutManager = layoutManager
            rvMovieList.setHasFixedSize(true)
            rvMovieList.adapter = adapter
        }
    }

    companion object {
        fun newInstance(movieId: Int): MovieReviewsFragment {
            return MovieReviewsFragment().apply {
                val args = newInstanceBundle(movieId)
                arguments = args
            }
        }

        private fun newInstanceBundle(movieId: Int): Bundle {
            return Bundle().apply {
                putInt(KEY_MOVIE_ID, movieId)
            }
        }
    }
}