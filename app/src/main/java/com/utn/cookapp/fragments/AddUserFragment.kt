package com.utn.cookapp.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
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
    private var imageUri : Uri? = null
    private val pickImage = 100

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
            if(usrPlainText.text.toString() == null)
            {
                Snackbar.make(v,"Debe ingresar un nopmbre de usuario.", Snackbar.LENGTH_SHORT).show()
            }
            else if(passPlainText.text.toString() == null)
            {
                Snackbar.make(v,"Debe ingresar una contraseña.", Snackbar.LENGTH_SHORT).show()
            }
            else if(agePlainText.text.toString() == null)
            {
                Snackbar.make(v,"Debe ingresar su edad.", Snackbar.LENGTH_SHORT).show()
            }
            else
            {
                userDao?.insertPerson(
                    User(
                        userList.size+1,
                        usrPlainText.text.toString(),
                        passPlainText.text.toString(),
                        agePlainText.text.toString(),
                        imageUri.toString(),
                    )
                )
                v.findNavController().popBackStack()
            }
        }
        selectImageBtn.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddUserViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            profileView.setImageURI(imageUri)
        }
    }
}