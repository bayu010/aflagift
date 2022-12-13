package com.example.alfagift.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alfagift.R
import com.example.alfagift.databinding.ActivityMainBinding
import com.example.alfagift.fragment.DashboardFragment
import com.example.alfagift.fragment.MovieInfoFragment

class MovieInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val movieId by lazy { intent?.getIntExtra(KEY_MOVIE_ID, 0)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_fragment, MovieInfoFragment.newInstance(movieId = movieId!!))
            addToBackStack(null)
            commit()
        }
    }

    override fun onBackPressed() {
        val backStackSum = supportFragmentManager.backStackEntryCount
        if (backStackSum > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    companion object {
        const val KEY_MOVIE_ID = "MOVIE_ID"

        fun launchIntent(context: Context, movieId: Int) {
            val intent = Intent(context, MovieInfoActivity::class.java)
            intent.putExtra(KEY_MOVIE_ID, movieId)
            context.startActivity(intent)
        }
    }
}