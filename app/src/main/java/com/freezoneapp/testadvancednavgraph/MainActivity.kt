package com.freezoneapp.testadvancednavgraph

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigation: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_root)
        val navHostFragment: Fragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_root)!!
        val navigator = KeepStateNavigator(
            context = this,
            manager = navHostFragment.childFragmentManager,
            containerId = R.id.nav_host_root,
            navController = navController
        )

        navController.navigatorProvider.plusAssign(navigator)
        navController.setGraph(R.navigation.graph_root)

        var allowReselectedNav = true

        navigation.setOnNavigationItemReselectedListener {
            if (allowReselectedNav) {
                val builder = NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setEnterAnim(R.anim.nav_default_enter_anim)
                    .setExitAnim(R.anim.nav_default_exit_anim)
                    .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                    .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
                val options = builder.build()
                navController.navigate(it.itemId, bundleOf(CREATE_PARAM to true), options)
                allowReselectedNav = false
            }
        }
        navigation.setOnNavigationItemSelectedListener {
            val builder = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.nav_default_enter_anim)
                .setExitAnim(R.anim.nav_default_exit_anim)
                .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
            val options = builder.build()
            try {
                //TODO provide proper API instead of using Exceptions as Control-Flow.
                navController.navigate(it.itemId, null, options)
                allowReselectedNav = true
                true
            } catch (e: IllegalArgumentException) {
                false
            }
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val menu = navigation.menu
            for (h in 0 until menu.size()) {
                val item: MenuItem = menu.getItem(h)
                if (matchDestination(destination, item.itemId)) {
                    item.isChecked = true
                }
            }
        }
    }

    private fun matchDestination(
        destination: NavDestination,
        @IdRes destId: Int
    ): Boolean {
        var currentDestination: NavDestination? = destination
        while (currentDestination!!.id != destId && currentDestination.parent != null) {
            currentDestination = currentDestination.parent
        }
        return currentDestination.id == destId
    }

}
