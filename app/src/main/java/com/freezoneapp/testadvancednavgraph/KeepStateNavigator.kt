package com.freezoneapp.testadvancednavgraph

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import java.util.*


@Navigator.Name("keep_state_fragment") // `keep_state_fragment` is used in navigation xml
class KeepStateNavigator(
    private val context: Context,
    private val manager: FragmentManager, // Should pass childFragmentManager.
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    private val mBackStack = ArrayDeque<Int>()

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        logD("On navigate ${destination.label}")
        logD("On navigate ${destination.id}")
        val tag = destination.id.toString()
        val transaction = manager.beginTransaction()

        var initialNavigate = false
        val currentFragment = manager.primaryNavigationFragment
        if (currentFragment != null) {
            transaction.detach(currentFragment)
        } else {
            initialNavigate = true
        }

        var fragment = manager.findFragmentByTag(tag)
        logD("fragment is null : ${fragment == null}")
        if (fragment == null) {
            val className = destination.className
            fragment = manager.fragmentFactory.instantiate(context.classLoader, className)
            transaction.add(containerId, fragment, tag)
        } else {
            transaction.attach(fragment)
        }
        if (!initialNavigate) {
            transaction.addToBackStack((mBackStack.size + 1).toString() + "-" + destination.id.toString())
        }

        transaction.setPrimaryNavigationFragment(fragment)
        transaction.setReorderingAllowed(true)
        transaction.commit()

        logD("is initialNavigate : $initialNavigate")        // The commit succeeded, update our view of the world

        mBackStack.add(destination.id)
        return destination
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
        logD(
            "Back! ${generateBackStackName(mBackStack.size, mBackStack.peekLast()!!)}"
        )
        manager.popBackStack(
            generateBackStackName(mBackStack.size, mBackStack.peekLast()!!), -1
        )
        mBackStack.removeLast()

        return true
    }

    private fun generateBackStackName(backStackIndex: Int, destId: Int): String {
        return "$backStackIndex-$destId"
    }
}
