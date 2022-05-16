package com.utn.cookapp.fragments

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.utn.cookapp.R
import com.utn.cookapp.database.recipeDao
import com.utn.cookapp.database.recipeDatabase
import com.utn.cookapp.entities.Recipe
import com.utn.cookapp.viewmodels.DetailRecipeViewModel

class DetailRecipeFragment : Fragment() {

    //Views
    private lateinit var viewModel: DetailRecipeViewModel
    private lateinit var v : View
    private lateinit var recipeText : TextView
    //Database
    private var db: recipeDatabase? = null
    private var recipeDao: recipeDao? = null
    //Variables
    private lateinit var recipeSelected : Recipe

    companion object {
        fun newInstance() = DetailRecipeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Views configuration
        v = inflater.inflate(R.layout.detail_recipe_fragment, container, false)
        recipeText = v.findViewById(R.id.recipeText)

        return v
    }

    override fun onStart() {
        super.onStart()
        //Shared preferences init
        val sharedPref : SharedPreferences = requireContext().getSharedPreferences("myPref", Context.MODE_PRIVATE)
        //Database load
        db = recipeDatabase.getAppDataBase(v.context)
        recipeDao = db?.recipeDao()
        //Select recipe from database
        recipeSelected = recipeDao?.loadPersonById(sharedPref.getInt("recipeSelected",-1)) as Recipe
        //Set interface
        recipeText.text = recipeSelected.recipe

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailRecipeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}