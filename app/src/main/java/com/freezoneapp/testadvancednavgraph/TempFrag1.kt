package com.freezoneapp.testadvancednavgraph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class TempFrag1 : Fragment() {

    companion object {
        fun newInstance() = TempFrag1()
    }

    private lateinit var viewModel: TempFrag1ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.temp_frag1_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TempFrag1ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}