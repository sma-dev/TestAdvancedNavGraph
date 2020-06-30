package com.freezoneapp.testadvancednavgraph.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.freezoneapp.testadvancednavgraph.R

class HomeSeqFragment2 : Fragment() {

    companion object {
        fun newInstance() = HomeSeqFragment2()
    }

    private lateinit var viewModel: HomeSeqFragment2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.home_seq_fragment2_fragment, container, false)
        
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeSeqFragment2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}