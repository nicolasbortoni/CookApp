package com.utn.cookapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.utn.cookapp.R
import com.utn.cookapp.viewmodels.RecyclerViewViewModel

class RecyclerViewFragment : Fragment() {

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    private lateinit var viewModel: RecyclerViewViewModel
    private lateinit var v : View
    private lateinit var addButton : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.recycler_view_fragment, container, false)
        addButton = v.findViewById(R.id.addBtn)
        return v
    }

    override fun onStart() {
        super.onStart()
        addButton.setOnClickListener {
            val action = RecyclerViewFragmentDirections.actionRecyclerViewFragmentToAddFragment()
            v.findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecyclerViewViewModel::class.java)
        // TODO: Use the ViewModel
    }

}