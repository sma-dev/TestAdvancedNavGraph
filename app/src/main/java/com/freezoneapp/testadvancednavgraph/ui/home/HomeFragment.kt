package com.freezoneapp.testadvancednavgraph.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.freezoneapp.testadvancednavgraph.R
import com.freezoneapp.testadvancednavgraph.ui.NavManager

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var navManager: NavManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        navManager = NavManager(
            fragmentManager = childFragmentManager,
            fragmentTag = "frag#0",
            containerId = R.id.home_nav_host,
            navGraphId = R.navigation.nav_home,
            isPrimaryNavFragment = true
        )
        return root
    }
}