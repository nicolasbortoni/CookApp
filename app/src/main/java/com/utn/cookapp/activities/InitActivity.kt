package com.utn.cookapp.activities

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.utn.cookapp.R
import com.utn.cookapp.fragments.LoginFragment

class InitActivity : AppCompatActivity() {

    private lateinit var fragmentContainer : FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {



        val prefs = PreferenceManager.getDefaultSharedPreferences(this)

        if(prefs.getBoolean("theme",false)){
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)

    }



}