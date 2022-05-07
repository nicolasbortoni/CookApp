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
import android.widget.EditText
import android.widget.TextView
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

    private lateinit var viewModel: LoginViewModel
    private lateinit var v : View

    private lateinit var userPlainText : EditText
    private lateinit var passPlainText : EditText
    private lateinit var loginBtn : Button
    private lateinit var newAcountTextView : TextView

    private var db: userDatabase? = null
    private var userDao: userDao? = null

    private lateinit var userList: MutableList<User>
    private lateinit var userAux : User

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.login_fragment, container, false)
        userPlainText = v.findViewById(R.id.userPlainText)
        passPlainText = v.findViewById(R.id.passPlainText)
        loginBtn = v.findViewById(R.id.loginBtn)
        newAcountTextView = v.findViewById(R.id.newAcountTextView)
        return v
    }

    override fun onStart() {
        super.onStart()

        val sharedPref : SharedPreferences = requireContext().getSharedPreferences("myPref",Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.commit()

        db = userDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()

        userList = userDao?.loadAllPersons() as MutableList<User>

        loginBtn.setOnClickListener {
            var userAux = userList.find { it.user == userPlainText.text.toString() }

            if (userAux == null) {
                Snackbar.make(v,"Nombre de usuario inválido", Snackbar.LENGTH_SHORT).show()
                userPlainText.text = null
                passPlainText.text = null
                userPlainText.setHintTextColor(Color.parseColor("#ff0b16"))
            }
            else{
                if(userAux.pass == passPlainText.text.toString()){
                    userPlainText.text = null
                    passPlainText.text = null
                    editor.putInt("userLoged",userAux.id)
                    editor.apply()
                    
                    val action = LoginFragmentDirections.actionLoginFragmentToMainActivity()
                    v.findNavController().navigate(action)
                }
                else{
                    Snackbar.make(v,"Contraseña incorrecta", Snackbar.LENGTH_SHORT).show()
                    passPlainText.text = null
                    passPlainText.setHintTextColor(Color.parseColor("#ff0b16"))
                }
            }
        }
        newAcountTextView.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToAddUserFragment()
            v.findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        // TODO: Use the ViewModel
    }

}