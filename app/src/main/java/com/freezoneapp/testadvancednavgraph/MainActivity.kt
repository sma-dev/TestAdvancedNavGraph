package com.freezoneapp.testadvancednavgraph

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.get
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var baseFragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)
        baseFragmentManager =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.childFragmentManager!!

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        var reSelectedTag = 0

        baseFragmentManager.addOnBackStackChangedListener {
            Log.d("NavService", baseFragmentManager.backStackEntryCount.toString())
        }

        navController.navigate(R.id.navigation_home)
        navView.setOnNavigationItemReselectedListener {
            if (reSelectedTag == 0) {
                Log.d("NavService", "double!")
                navController.popBackStack(it.itemId, true)
                navController.navigate(it.itemId)
                reSelectedTag = it.itemId
            }
        }
        navView.setOnNavigationItemSelectedListener { item ->
            when {
                navController.currentDestination != navController.graph[item.itemId] -> {
                    reSelectedTag = 0
                    if (baseFragmentManager.isOnBackStack(item.itemId)) {
                        Log.d("NavService", "back!")
                        navController.popBackStack(item.itemId, false)
                    } else {
                        Log.d("NavService", "nav!")
                        navController.navigate(item.itemId)
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        when (navController.currentDestination) {
            navController.graph[R.id.navigation_home] -> {
                if (baseFragmentManager.backStackEntryCount > 1) {
                    navController.popBackStack()
                } else {
                    moveTaskToBack(true)
                    finish()
                }
            }
            else -> {
                super.onBackPressed()
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
