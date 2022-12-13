package com.example.alfagift.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.room.Room
import com.example.alfagift.R
import com.example.alfagift.activity.MovieInfoActivity.Companion.KEY_MOVIE_ID
import com.example.alfagift.activity.ReviewsActivity
import com.example.alfagift.activity.SecondActivity
import com.example.alfagift.databinding.FragmentMovieInfoBinding
import com.example.alfagift.db.MoviesDatabase
import com.example.alfagift.fragment.core.CoreFragment
import com.example.alfagift.model.MovieModel
import com.example.alfagift.model.db.Movies
import com.example.alfagift.model.util.Resource
import com.example.alfagift.utils.MessageDialog
import com.example.alfagift.utils.ProgressDialog
import com.example.alfagift.viewmodel.MovieInfoViewModel
import com.example.alfagift.viewmodel.MovieVideosViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MovieInfoFragment: CoreFragment() {
    override val viewBinding by lazy { FragmentMovieInfoBinding.inflate(layoutInflater) }
    private val viewModelVideo by lazy { MovieVideosViewModel() }
    private val viewModel by lazy { MovieInfoViewModel() }
    private val movieId by lazy { arguments?.getInt(KEY_MOVIE_ID)}
    private val listGenre by lazy { ArrayList<String>()}
    private lateinit var dbMovie: MoviesDatabase
    private var titleMovie = ""
    private var posterPath = ""
    private var ratingMovie = 0.0
    private var statusMovie = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        lifecycle.addObserver(viewBinding.ytpTrailer)
        viewBinding.tbInfoMovie.toolbarTitle.text = resources.getString(R.string.app_name)
        dbMovie = Room.databaseBuilder(requireContext(), MoviesDatabase::class.java, "movies-db").build()

        if (movieId != null || movieId != 0){
            viewModelVideo.getMovieVideos(movieId = movieId!!)
        }else{
            MessageDialog.show(
                context = requireContext(),
                title = resources.getString(R.string.label_error),
                message = "Movie ID is not found"
            )
        }

        viewBinding.btnSaveMovie.setOnClickListener {
            insertDataMovie()
        }
        viewBinding.tbInfoMovie.ivFavorite.setOnClickListener {
            SecondActivity.launchIntent(requireContext())
        }
        viewBinding.tvReviews.setOnClickListener {
            ReviewsActivity.launchIntent(requireContext(), movieId = movieId!!)
        }
    }

    private fun initViewModel(){
        viewModelVideo.state.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.LOADING -> {
                    ProgressDialog.show(requireContext())
                }
                Resource.SUCCESS -> {
                    ProgressDialog.dismiss()
                    viewModel.getInfoMovie(movieId = movieId!!)
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

        viewModelVideo.dataList.observe(viewLifecycleOwner){
            if (it != null){
                it.results?.forEach { video ->
                    if (video.name.equals("Official Trailer", true) && video.videoType.equals("Trailer", true)){
                        playMovieTrailer(video.videoKey ?: "")
                    }
                }
            }
        }
        lifecycle.addObserver(viewModelVideo)

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

        viewModel.liveData.observe(viewLifecycleOwner){
            if (it != null){
                setMovieInfo(it)
            }
        }
        lifecycle.addObserver(viewModel)
    }

    @SuppressLint("SetTextI18n")
    private fun setMovieInfo(data: MovieModel){
        data.genres?.forEach{
            listGenre.add(it.genreName!!)
        }
        val genre = TextUtils.join(", ", listGenre)

        viewBinding.apply {
            tvMovieTitle.text = data.movieTitle
            tvMovieGenres.text = genre
            tvMovieDuration.text = "${data.runTime.toString()} mins"
            tvMovieOverview.text = data.overview
        }

        titleMovie = data.movieTitle ?: ""
        posterPath = data.posterPath ?: ""
        ratingMovie = data.voteAverage ?: 0.0
        statusMovie = data.movieStatus ?: ""
    }

    private fun playMovieTrailer(videoKey: String) {
        if (videoKey.isNotEmpty()) {
            viewBinding.ytpTrailer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoKey, 0f)
                }
            })
        } else {
            Toast.makeText(requireContext(), "Trailer is not found", Toast.LENGTH_SHORT).show()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun insertDataMovie(){
        GlobalScope.launch {
            val movie = Movies(movieId = movieId, movieTitle = titleMovie, posterPath = posterPath, voteRating = ratingMovie, statusMovie = statusMovie)
            dbMovie.moviesDao().insertMovies(movie)
        }
        Toast.makeText(requireContext(), "Movie has been added to Favorite", Toast.LENGTH_SHORT).show()
        SecondActivity.launchIntent(requireContext())
    }

    companion object{

        fun newInstance(movieId: Int): MovieInfoFragment{
            return MovieInfoFragment().apply {
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