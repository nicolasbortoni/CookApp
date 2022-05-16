package com.utn.cookapp.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import com.utn.cookapp.R
import com.utn.cookapp.viewmodels.SplashViewModel

class SplashFragment : Fragment() {

    private lateinit var viewModel: SplashViewModel
    private lateinit var v : View

    companion object {
        fun newInstance() = SplashFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.splash_fragment, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()

        Handler().postDelayed(

            {
                val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                v.findNavController().navigate(action)
            }
            , 1000)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        // TODO: Use the ViewModel
    }

}