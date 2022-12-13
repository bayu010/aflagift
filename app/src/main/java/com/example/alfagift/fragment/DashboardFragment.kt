package com.example.alfagift.fragment

import android.os.Bundle
import android.view.View
import com.example.alfagift.R
import com.example.alfagift.activity.SecondActivity
import com.example.alfagift.databinding.FragmentDashboardBinding
import com.example.alfagift.fragment.core.CoreFragment

class DashboardFragment: CoreFragment() {
    override val viewBinding by lazy { FragmentDashboardBinding.inflate(layoutInflater) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.tbDashboard.toolbarTitle.text = resources.getString(R.string.app_name)

        activity?.supportFragmentManager!!.beginTransaction().apply {
            replace(R.id.fl_top_rated, TopRatedFragment.newInstance())
            commit()
        }

        activity?.supportFragmentManager!!.beginTransaction().apply {
            replace(R.id.fl_now_playing, NowPlayingFragment.newInstance())
            commit()
        }

        viewBinding.tbDashboard.ivFavorite.setOnClickListener {
            SecondActivity.launchIntent(requireContext())
        }
    }

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }
}