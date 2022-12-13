package com.example.alfagift.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alfagift.R
import com.example.alfagift.activity.MovieInfoActivity
import com.example.alfagift.adapter.MovieListAdapter
import com.example.alfagift.databinding.LayoutMovieListBinding
import com.example.alfagift.fragment.core.CoreFragment
import com.example.alfagift.model.util.Resource
import com.example.alfagift.utils.MessageDialog
import com.example.alfagift.utils.ProgressDialog
import com.example.alfagift.viewmodel.NowPlayingViewModel

class NowPlayingFragment: CoreFragment() {
    override val viewBinding by lazy { LayoutMovieListBinding.inflate(layoutInflater) }
    private val viewModel by lazy { NowPlayingViewModel() }
    private val adapter = MovieListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()
        viewModel.getNowPlayingMovie()
        initView()
    }

    private fun initView(){
        viewBinding.tvMovieCategory.text = resources.getString(R.string.text_now_playing)
        viewBinding.tvSeeMore.setOnClickListener {
            Toast.makeText(requireContext(), "See more is clicked", Toast.LENGTH_SHORT).show()
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
            if (it.resultMovies != null){
                adapter.setDataList(it.resultMovies)
            }else{
                viewBinding.rvMovieList.visibility = View.GONE
                viewBinding.tvEmptyText.visibility = View.VISIBLE
            }
        }
        lifecycle.addObserver(viewModel)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        viewBinding.apply {
            rvMovieList.layoutManager = layoutManager
            rvMovieList.setHasFixedSize(true)
            rvMovieList.adapter = adapter
        }

        adapter.setOnClickedCallback(object: MovieListAdapter.OnItemClickCallback{
            override fun onItemClicked(movieId: Int) {
                MovieInfoActivity.launchIntent(requireContext(), movieId = movieId)
            }
        })
    }

    companion object {
        fun newInstance(): NowPlayingFragment {
            return NowPlayingFragment()
        }
    }
}