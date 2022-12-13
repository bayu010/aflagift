package com.example.alfagift.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alfagift.R
import com.example.alfagift.databinding.ActivityMainBinding
import com.example.alfagift.fragment.DashboardFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_fragment, DashboardFragment.newInstance())
            addToBackStack(null)
            commit()
        }
    }

    override fun onBackPressed() {
        val backStackSum = supportFragmentManager.backStackEntryCount
        if (backStackSum > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finishAffinity()
        }
    }
}