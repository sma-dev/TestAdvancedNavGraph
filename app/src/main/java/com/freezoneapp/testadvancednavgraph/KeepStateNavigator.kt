package com.freezoneapp.testadvancednavgraph

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import java.util.*

const val CREATE_PARAM = "create"

@Navigator.Name("keep_state_fragment") // `keep_state_fragment` is used in navigation xml
class KeepStateNavigator(
    private val context: Context,
    private val manager: FragmentManager, // Should pass childFragmentManager.
    private val containerId: Int,
    private val navController: NavController
) : FragmentNavigator(context, manager, containerId) {

    private val mBackStack = ArrayDeque<Destination>()
    private var initialId: Int = 0

    @SuppressLint("RestrictedApi")
    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        var create = false
        args?.let {
            create = it.getBoolean(CREATE_PARAM)
        }
        val tag = destination.id.toString()
        val transaction = manager.beginTransaction()

        var initialNavigate = false
        val containsInBackStack = mBackStack.contains(destination) && destination.id != initialId
        val currentFragment = manager.primaryNavigationFragment
        if (currentFragment != null) {
            transaction.detach(currentFragment)
        } else {
            initialNavigate = true
            initialId = destination.id
        }


        var fragment = manager.findFragmentByTag(tag)

        if (create && fragment != null) {
            transaction.remove(fragment)
        }
        if (create || fragment == null) {
            val className = destination.className
            fragment = manager.fragmentFactory.instantiate(context.classLoader, className)
            transaction.add(containerId, fragment, tag)
        } else {
            transaction.attach(fragment)
        }

        if (!initialNavigate) {
            if (containsInBackStack) {
                navController.backStack.remove(
                    navController.backStack.elementAt(mBackStack.indexOf(destination) + 1)
                )
                mBackStack.remove(destination)
            }
        }

        transaction.setPrimaryNavigationFragment(fragment)
        transaction.setReorderingAllowed(true)
        transaction.commit()

        logD(" ")
        mBackStack.forEach { logD(it.label.toString()) }

        return if (!create) {
            mBackStack.add(destination)
            destination
        } else null
    }

    override fun popBackStack(): Boolean {
        if (mBackStack.isEmpty()) {
            return false
        }
        if (manager.isStateSaved) {
            logD(
                "Ignoring popBackStack() call: FragmentManager has already"
                        + " saved its state"
            )
            return false
        }
        if (mBackStack.peekLast() == null) {
            logD("peekLast is null")
            return false
        }

        mBackStack.removeLast()
        val backDest = mBackStack.last
        mBackStack.removeLast()
        navigate(backDest, null, null, null)

        return true
    }
}
