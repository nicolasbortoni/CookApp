package com.utn.cookapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Base64.decode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.utn.cookapp.R
import com.utn.cookapp.database.userDao
import com.utn.cookapp.database.userDatabase
import com.utn.cookapp.entities.User
import com.utn.cookapp.viewmodels.ProfileViewModel
import java.lang.Byte.decode
import java.util.*
import kotlin.math.log

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }
    //Views
    private lateinit var viewModel: ProfileViewModel
    private lateinit var v : View
    private lateinit var settingsBtn : Button
    private lateinit var logOutBtn : Button
    private lateinit var profileImage : ImageView
    private lateinit var usernameTextView: TextView
    private lateinit var ageTextView: TextView
    //Database
    private var db: userDatabase? = null
    private var userDao: userDao? = null
    //Variables
    private lateinit var userLoged : User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Views configuration
        v = inflater.inflate(R.layout.profile_fragment, container, false)
        settingsBtn = v.findViewById(R.id.settingsBtn)
        profileImage = v.findViewById(R.id.profileImage)
        usernameTextView = v.findViewById(R.id.usernameText)
        ageTextView = v.findViewById(R.id.ageText)
        logOutBtn = v.findViewById(R.id.logOutBtn)

        return v
    }

    override fun onStart() {
        super.onStart()

        //Shared preferences init
        val sharedPref : SharedPreferences = requireContext().getSharedPreferences("myPref",Context.MODE_PRIVATE)
        //Database load
        db = userDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()
        //User information from database
        userLoged = userDao?.loadPersonById(sharedPref.getInt("userLoged",-1)) as User

        //Set interface
        usernameTextView.text = "Username: " + userLoged.user
        ageTextView.text = "Age: " + userLoged.age

        val imageBytes = Base64.getDecoder().decode(userLoged.profileImage)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

        profileImage.setImageBitmap(image)

        settingsBtn.setOnClickListener {
            //Navigate to SettingsActivity
            val action = ProfileFragmentDirections.actionProfileFragmentToSettingsActivity()
            v.findNavController().navigate(action)
        }
        logOutBtn.setOnClickListener {
            //Finish activity and refresh shared preferences
            val editor = sharedPref.edit()
            editor.putBoolean("reminder",false)
            editor.apply()
            requireActivity().finish()
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}