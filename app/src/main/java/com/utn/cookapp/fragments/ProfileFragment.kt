package com.utn.cookapp.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.utn.cookapp.R
import com.utn.cookapp.viewmodels.ProfileViewModel

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var v : View
    private lateinit var settingsBtn : Button
    private lateinit var profileImage : ImageView
    private lateinit var usernameTextView: TextView
    private lateinit var ageTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.profile_fragment, container, false)
        settingsBtn = v.findViewById(R.id.settingsBtn)
        profileImage = v.findViewById(R.id.profileImage)
        usernameTextView = v.findViewById(R.id.usernameText)
        ageTextView = v.findViewById(R.id.ageText)
        return v
    }

    override fun onStart() {
        super.onStart()

        val sharedPref : SharedPreferences = requireContext().getSharedPreferences("myPref",Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        usernameTextView.setText(getString(R.string.app_user,sharedPref.getString("userLoged","default")))
        ageTextView.setText(getString(R.string.app_age,sharedPref.getString("ageLoged","default")))
        profileImage.setImageURI(sharedPref.getString("profileImageLoged",null)?.toUri())

        settingsBtn.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToSettingsActivity()
            v.findNavController().navigate(action)
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}