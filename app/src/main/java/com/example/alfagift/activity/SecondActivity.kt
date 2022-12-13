package com.example.alfagift.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alfagift.R
import com.example.alfagift.databinding.ActivityMainBinding
import com.example.alfagift.fragment.DashboardFragment
import com.example.alfagift.fragment.FavoriteMovieFragment
import com.example.alfagift.fragment.MovieInfoFragment

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_fragment, FavoriteMovieFragment.newInstance())
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
        fun launchIntent(context: Context) {
            val intent = Intent(context, SecondActivity::class.java)
            context.startActivity(intent)
        }
    }
}