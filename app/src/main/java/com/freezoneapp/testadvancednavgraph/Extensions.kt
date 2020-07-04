package com.freezoneapp.testadvancednavgraph

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment

inline fun <reified T> T.logD(s: String) {
    val tag = T::class.java.simpleName
    Log.d(tag, s)
}

fun Fragment.toast(s: String) {
    Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show()
}
