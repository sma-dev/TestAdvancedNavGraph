package com.freezoneapp.testadvancednavgraph.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.freezoneapp.testadvancednavgraph.R
import com.freezoneapp.testadvancednavgraph.ui.NavManager

class HomeFragment : Fragment() {

    private lateinit var navManager: NavManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navManager = NavManager(
            fragmentManager = childFragmentManager,
            fragmentTag = R.id.navigation_home.toString(),
            containerId = R.id.nav_container_home,
            navGraphId = R.navigation.graph_home,
            isPrimaryNavFragment = true
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }
}