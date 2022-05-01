package com.utn.cookapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.utn.cookapp.R
import com.utn.cookapp.viewmodels.DetailContainerViewModel

class DetailContainerFragment : Fragment() {

    companion object {
        fun newInstance() = DetailContainerFragment()
    }

    private lateinit var viewModel: DetailContainerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_container_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailContainerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}