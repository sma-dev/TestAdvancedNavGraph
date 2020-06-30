package com.freezoneapp.testadvancednavgraph.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.freezoneapp.testadvancednavgraph.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)


        val fragmentManager: FragmentManager = childFragmentManager
        val containerId: Int = R.id.nav_host_container
        val fragmentTag = getFragmentTag(0)
        val navHostFragment = obtainNavHostFragment(
            fragmentManager,
            fragmentTag,
            R.navigation.home_nav,
            containerId
        )

        attachNavHostFragment(fragmentManager, navHostFragment, true)

        /*val selectedFragment = fragmentManager.findFragmentByTag(fragmentTag)!!

        fragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.nav_default_enter_anim,
                R.anim.nav_default_exit_anim,
                R.anim.nav_default_pop_enter_anim,
                R.anim.nav_default_pop_exit_anim
            )
            .attach(selectedFragment)
            .setPrimaryNavigationFragment(selectedFragment)
           *//* .apply {
                // Detach all other Fragments
                graphIdToTagMap.forEach { _, fragmentTagIter ->
                    if (fragmentTagIter != newlySelectedItemTag) {
                        detach(fragmentManager.findFragmentByTag(firstFragmentTag)!!)
                    }
                }
            }
            .addToBackStack(firstFragmentTag)*//*
            .setReorderingAllowed(true)
            .commit()*/


        // Optional: on item reselected, pop back stack to the destination of the graph
        //setupItemReselected(graphIdToTagMap, fragmentManager)

        // Handle deep link
        //setupDeepLinks(navGraphIds, fragmentManager, containerId, intent)

        // Finally, ensure that we update our BottomNavigationView when the back stack changes

        /*fragmentManager.addOnBackStackChangedListener {
            if (!isOnFirstFragment && !fragmentManager.isOnBackStack(firstFragmentTag)) {
                this.selectedItemId = firstFragmentGraphId
            }

            // Reset the graph if the currentDestination is not valid (happens when the back
            // stack is popped after using the back button).
            selectedNavController.value?.let { controller ->
                if (controller.currentDestination == null) {
                    controller.navigate(controller.graph.id)
                }
            }
        }*/


        return root
    }


    private fun detachNavHostFragment(
        fragmentManager: FragmentManager,
        navHostFragment: NavHostFragment
    ) {
        fragmentManager.beginTransaction()
            .detach(navHostFragment)
            .commitNow()
    }

    private fun attachNavHostFragment(
        fragmentManager: FragmentManager,
        navHostFragment: NavHostFragment,
        isPrimaryNavFragment: Boolean
    ) {
        fragmentManager.beginTransaction()
            .attach(navHostFragment)
            .apply {
                if (isPrimaryNavFragment) {
                    setPrimaryNavigationFragment(navHostFragment)
                }
            }
            .commitNow()

    }

    private fun obtainNavHostFragment(
        fragmentManager: FragmentManager,
        fragmentTag: String,
        navGraphId: Int,
        containerId: Int
    ): NavHostFragment {
        // If the Nav Host fragment exists, return it
        val existingFragment = fragmentManager.findFragmentByTag(fragmentTag) as NavHostFragment?
        existingFragment?.let { return it }

        // Otherwise, create it and return it.
        val navHostFragment = NavHostFragment.create(navGraphId)
        fragmentManager.beginTransaction()
            .add(containerId, navHostFragment, fragmentTag)
            .commitNow()
        return navHostFragment
    }

    private fun getFragmentTag(index: Int) = "bottomNavigation#$index"

    private fun FragmentManager.isOnBackStack(backStackName: String): Boolean {
        val backStackCount = backStackEntryCount
        for (index in 0 until backStackCount) {
            if (getBackStackEntryAt(index).name == backStackName) {
                return true
            }
        }
        return false
    }

}