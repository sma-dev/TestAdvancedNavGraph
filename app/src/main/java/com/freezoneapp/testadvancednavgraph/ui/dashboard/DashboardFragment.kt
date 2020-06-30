package com.freezoneapp.testadvancednavgraph.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.freezoneapp.testadvancednavgraph.R
import com.freezoneapp.testadvancednavgraph.ui.NavManager

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var navManager: NavManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        navManager = NavManager(
            fragmentManager = childFragmentManager,
            fragmentTag = "frag#1",
            containerId = R.id.dashboard_home_nav,
            navGraphId = R.navigation.nav_dashboard,
            isPrimaryNavFragment = true
        )
        return root
    }
}