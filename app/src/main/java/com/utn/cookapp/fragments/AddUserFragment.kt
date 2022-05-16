package com.utn.cookapp.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64.encodeToString
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.utn.cookapp.R
import com.utn.cookapp.database.userDao
import com.utn.cookapp.database.userDatabase
import com.utn.cookapp.entities.User
import com.utn.cookapp.viewmodels.AddUserViewModel
import java.io.ByteArrayOutputStream
import java.util.*

class AddUserFragment : Fragment() {

    private lateinit var viewModel: AddUserViewModel
    private lateinit var v :View

    private lateinit var usrPlainText : EditText
    private lateinit var agePlainText : EditText
    private lateinit var passPlainText : EditText
    private lateinit var profileView : ImageView
    private lateinit var selectImageBtn : FloatingActionButton
    private lateinit var doneBtn : Button

    private var db: userDatabase? = null
    private var userDao: userDao? = null

    private lateinit var userList: MutableList<User>
    private var imageBitmap : Bitmap? = null

    companion object {
        fun newInstance() = AddUserFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.add_user_fragment, container, false)

        usrPlainText = v.findViewById(R.id.usernamePlainText)
        passPlainText = v.findViewById(R.id.passwordPlainText)
        agePlainText = v.findViewById(R.id.agePlainText)
        profileView = v.findViewById(R.id.profileImage)
        selectImageBtn = v.findViewById(R.id.selectImageBtn)
        doneBtn = v.findViewById(R.id.doneButton)

        return v
    }

    override fun onStart() {

        super.onStart()

        db = userDatabase.getAppDataBase(v.context)
        userDao = db?.userDao()

        userList = userDao?.loadAllPersons() as MutableList<User>

        doneBtn.setOnClickListener {
            if(usrPlainText.text.isEmpty())
            {
                usrPlainText.error = "Debe ingresar un nombre de usuario"
            }
            else if(passPlainText.text.isEmpty())
            {
                passPlainText.error = "Debe ingresar una contraseña"
            }
            else if(agePlainText.text.isEmpty())
            {
                agePlainText.error = "Debe ingresar su edad"
            }
            else
            {
                userDao?.insertPerson(
                    User(
                        userList.size+1,
                        usrPlainText.text.toString(),
                        passPlainText.text.toString(),
                        agePlainText.text.toString(),
                        BitMapToString(imageBitmap)
                    )
                )
                v.findNavController().popBackStack()
            }
        }
        selectImageBtn.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,100)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK){
            imageBitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver,data?.data)
            profileView.setImageBitmap(imageBitmap)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddUserViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun BitMapToString(bitmap: Bitmap?): String {
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.getEncoder().encodeToString(b)
    }

}