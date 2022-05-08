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
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.utn.cookapp.R
import com.utn.cookapp.adapters.RecipeAdapter
import com.utn.cookapp.database.recipeDao
import com.utn.cookapp.database.recipeDatabase
import com.utn.cookapp.entities.Recipe
import com.utn.cookapp.viewmodels.RecyclerViewViewModel

class RecyclerViewFragment : Fragment() {

    private lateinit var viewModel: RecyclerViewViewModel
    //Views
    private lateinit var v : View
    private lateinit var addButton : FloatingActionButton
    private lateinit var editButton : FloatingActionButton
    private lateinit var trashButton : FloatingActionButton
    private lateinit var recyclerView : RecyclerView
    //Variables
    private var selectedPosition : Int = -2
    private lateinit var selectedRecipe : Recipe
    private var nullRecipe = Recipe(-1,"","","","")
    private lateinit var recipeList : MutableList<Recipe>
    //Database
    private var db: recipeDatabase? = null
    private var recipeDao: recipeDao? = null
    //Lambdas
    val funct1 = { index : Int -> onLongItemClick(index) }
    val funct2 = { index : Int -> onItemClick(index) }

    companion object {
        fun newInstance() = RecyclerViewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Views configuration
        v = inflater.inflate(R.layout.recycler_view_fragment, container, false)
        addButton = v.findViewById(R.id.addBtn)
        recyclerView = v.findViewById(R.id.recyclerViewRecipes)
        editButton = v.findViewById(R.id.editBtn)
        trashButton = v.findViewById(R.id.trashBtn)

        return v
    }

    override fun onStart() {
        super.onStart()
        //Database load
        db = recipeDatabase.getAppDataBase(v.context)
        recipeDao = db?.recipeDao()
        //Recipe list from database
        recipeList = recipeDao?.loadAllPersons() as MutableList<Recipe>
        //RecyclerView configuration
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        //RecyclerView construction
        recyclerView.adapter = RecipeAdapter(recipeList,requireContext(),funct1,funct2,-2)


        addButton.setOnClickListener {
            //Navigation into AddFragment
            val action = RecyclerViewFragmentDirections.actionRecyclerViewFragmentToAddFragment(nullRecipe)
            v.findNavController().navigate(action)
        }

        trashButton.setOnClickListener{
            //Check for selected item
            if(selectedPosition < recipeList.size && selectedPosition>=0) {
                //Remove item from database - Refresh recipeList from database
                recipeDao?.delete(recipeList[selectedPosition])
                recipeList = recipeDao?.loadAllPersons() as MutableList<Recipe>
                //Refresh recyclerview
                recyclerView.adapter =
                    RecipeAdapter(recipeList, requireContext(), funct1, funct2, -2)
                selectedPosition = -2
            }
            trashButton.setVisibility(View.INVISIBLE)
            editButton.setVisibility(View.INVISIBLE)
        }

        editButton.setOnClickListener {
            //Check for selected item
            if(selectedPosition < recipeList.size && selectedPosition>=0){
                //Extract recipe from position to send it
                selectedRecipe = recipeList[selectedPosition]
                selectedPosition = -2
                //Navigation into AddFragment
                val action = RecyclerViewFragmentDirections.actionRecyclerViewFragmentToAddFragment(selectedRecipe)
                v.findNavController().navigate(action)
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecyclerViewViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun onLongItemClick(pos : Int) : Boolean{
        //Selection logic
        if(selectedPosition==pos){
            recyclerView.adapter = RecipeAdapter(recipeList, requireContext(), funct1, funct2, -2)
            selectedPosition = -2
            trashButton.setVisibility(View.INVISIBLE)
            editButton.setVisibility(View.INVISIBLE)
        }
        else {
            recyclerView.adapter = RecipeAdapter(recipeList, requireContext(), funct1, funct2, pos)
            selectedPosition = pos
            trashButton.setVisibility(View.VISIBLE)
            editButton.setVisibility(View.VISIBLE)
        }
        return true
    }
    fun onItemClick(id : Int){
        if(selectedPosition == -2){
            val sharedPref : SharedPreferences = requireContext().getSharedPreferences("myPref", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putInt("recipeSelected",id)
            editor.apply()
            val action = RecyclerViewFragmentDirections.actionRecyclerViewFragmentToDetailContainerFragment()
            v.findNavController().navigate(action)
        }
    }

}