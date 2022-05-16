package com.utn.cookapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.utn.cookapp.R
import com.utn.cookapp.database.recipeDao
import com.utn.cookapp.database.recipeDatabase
import com.utn.cookapp.database.userDao
import com.utn.cookapp.database.userDatabase
import com.utn.cookapp.entities.User
import com.utn.cookapp.viewmodels.LoginViewModel

class LoginFragment : Fragment() {

    //Views
    private lateinit var viewModel: LoginViewModel
    private lateinit var v : View
    private lateinit var userPlainText : EditText
    private lateinit var passPlainText : EditText
    private lateinit var loginBtn : Button
    private lateinit var newAcountTextView : TextView
    private lateinit var reminderCheckBox : CheckBox
    //Database
    private var db: userDatabase? = null
    private var userDao: userDao? = null
    //Variables
    private lateinit var userList: MutableList<User>

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Views configuration
        v = inflater.inflate(R.layout.login_fragment, container, false)
        userPlainText = v.findViewById(R.id.userPlainText)
        passPlainText = v.findViewById(R.id.passPlainText)
        loginBtn = v.findViewById(R.id.loginBtn)
        newAcountTextView = v.findViewById(R.id.newAcountTextView)
        reminderCheckBox = v.findViewById(R.id.reminderCheckBox)

        return v
    }

    override fun onStart() {
        super.onStart()

        //Shared preferences init
        val sharedPref : SharedPreferences = requireContext().getSharedPreferences("myPref",Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        //Check for loged user
        if(sharedPref.getBoolean("reminder",false)){
            val action = LoginFragmentDirections.actionLoginFragmentToMainActivity()
            v.findNavController().navigate(action)
        }
        else{
            editor.clear()
            editor.commit()
        }
        //Database load
        db = userDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()
        //User list from database
        userList = userDao?.loadAllPersons() as MutableList<User>

        loginBtn.setOnClickListener {

            //Load user from list
            var userAux = userList.find { it.user == userPlainText.text.toString() }

            //Login check logic
            if (userAux == null) {
                userPlainText.error = "Usuario inválido"
                userPlainText.text = null
            }
            else if(userAux.pass == passPlainText.text.toString()){

                userPlainText.text = null
                passPlainText.text = null

                if(reminderCheckBox.isChecked){
                    editor.putBoolean("reminder",true)
                }
                else{
                    editor.putBoolean("reminder",false)
                }
                editor.putInt("userLoged",userAux.id)
                editor.apply()

                val action = LoginFragmentDirections.actionLoginFragmentToMainActivity()
                v.findNavController().navigate(action)
            }
            else{
                passPlainText.error = "Contraseña incorrecta"
                passPlainText.text = null
            }
        }

        newAcountTextView.setOnClickListener {
            //Navigate to AddUserFragment
            val action = LoginFragmentDirections.actionLoginFragmentToAddUserFragment()
            v.findNavController().navigate(action)
        }

        //Override of function OnBackPressed
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed(){
                requireActivity().finish()
                return
            }
            })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}