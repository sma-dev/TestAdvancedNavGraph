package com.freezoneapp.testadvancednavgraph

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.get
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = findNavController(R.id.nav_host_fragment)
        val baseFm = navHostFragment?.childFragmentManager!!

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        var selected = 0
        baseFm.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        navView.setOnNavigationItemSelectedListener { item ->
            when {
                navController.currentDestination != navController.graph[item.itemId] -> {
                    selected = 0
                    if (baseFm.isOnBackStack(item.itemId)) {
                        Log.d("NavService", "back!")
                        navController.popBackStack(item.itemId, false)
                    } else {
                        Log.d("NavService", "nav!")
                        navController.navigate(item.itemId, null)
                    }
                    true
                }
                selected == 0 -> {
                    selected++
                    Log.d("NavService", "nav!")
                    navController.navigate(item.itemId, null)
                    true
                }
                else -> false
            }
        }
    }

    private fun FragmentManager.isOnBackStack(backStackId: Int): Boolean {
        val backStackCount = backStackEntryCount
        for (index in 0 until backStackCount) {
            if (getBackStackEntryAt(index).name!!.contains(backStackId.toString())) {
                return true
            }
        }
        return false
    }
}
