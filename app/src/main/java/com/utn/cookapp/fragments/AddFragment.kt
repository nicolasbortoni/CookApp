package com.utn.cookapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import com.utn.cookapp.R
import com.utn.cookapp.database.recipeDao
import com.utn.cookapp.database.recipeDatabase
import com.utn.cookapp.entities.Recipe
import com.utn.cookapp.viewmodels.AddViewModel

class AddFragment : Fragment() {

    private lateinit var viewModel: AddViewModel

    private lateinit var v : View

    private lateinit var recipeName : EditText
    private lateinit var author : EditText
    private lateinit var image : EditText
    private lateinit var recipe : EditText

    private lateinit var addBtn : Button

    private var db: recipeDatabase? = null
    private var recipeDao : recipeDao? = null
    private lateinit var recipeList : MutableList<Recipe>

    private var i :Int = 0

    companion object {
        fun newInstance() = AddFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.add_fragment, container, false)
        recipe = v.findViewById(R.id.recipePlainText)
        recipeName = v.findViewById(R.id.recipeNamePlainText)
        author = v.findViewById(R.id.authorPlainText)
        image = v.findViewById(R.id.imagePlainText)
        addBtn = v.findViewById(R.id.addRecipe)
        return v
    }

    override fun onStart() {
        super.onStart()
        db = recipeDatabase.getAppDataBase(v.context)
        recipeDao = db?.recipeDao()
        recipeList = recipeDao?.loadAllPersons() as MutableList<Recipe>

        addBtn.setOnClickListener {
            recipeDao?.insertPerson(Recipe(recipeList.size+1, recipeName.text.toString(),author.text.toString(),image.text.toString(), recipe.text.toString()))
            val action = AddFragmentDirections.actionAddFragmentToRecyclerViewFragment()
            v.findNavController().navigate(action)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        // TODO: Use the ViewModel
    }

}